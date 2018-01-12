package hnttest;

import java.awt.*;
import java.util.*;

public class Algorithm {
	
	int zzn=3;                              //柱子
	int pzn;                                //盘子个数
	Vector<Zz> zze;                         //柱子集合
	Vector<Pz> pze;                         //柱子集合
	Vector<Bz> bz;                          //移动步骤
	int bs=1;                               //记录步数  
	
	public Algorithm(int pz)                //构造函数
	{
		this.pzn=pz;
		this.pze=new Vector<Pz>();
		this.zze=new Vector<Zz>();
		this.bz=new Vector<Bz>();
		for(int i=0;i<this.zzn;i++)         //初始化柱子
		{
			Zz zz1=new Zz();
			zz1.zznu=i;
			zz1.pzn=0;
			zz1.pze=new Stack<Pz>();
			zz1.x=Data.zcx+i*Data.zd;
			zz1.y=Data.zcy;
			zz1.heigh=zz1.y;
			this.zze.add(zz1);
		}
		for(int i=0;i<this.pzn;i++)         //初始化盘子
		{
			Pz pz1=new Pz();
			pz1.l=Data.pzlmax-i*(Data.pzlmax-Data.pzlmin)/(this.pzn-1);
			pz1.h=pz1.l*Data.bl;
			pz1.zz=0;
			pz1.pznu=i;
			pz1.x=this.zze.get(0).x;
			pz1.y=(int) (this.zze.get(0).heigh-pz1.h/2);
			this.zze.get(0).heigh=(int) (this.zze.get(0).heigh-pz1.h);
			Color color = new Color(
					(new Double(Math.random() * 128)).intValue() + 128,
					(new Double(Math.random() * 128)).intValue() + 128,
					(new Double(Math.random() * 128)).intValue() + 128);
			pz1.color=color;
			this.zze.get(0).pze.add(pz1);
			this.pze.add(pz1);
		}
		this.zze.get(0).pzn=this.pzn;
	}
	
	
	public void hanoi(int n,int from,int denpend_on,int to)//将n个盘子由初始塔移动到目标塔(利用借用塔)  
	{  
	    if (n==1)move(1,from,to);//只有一个盘子是直接将初塔上的盘子移动到目的地  
	    else  
	    {  
	      hanoi(n-1,from,to,denpend_on);//先将初始塔的前n-1个盘子借助目的塔移动到借用塔上  
	      move(n,from,to);              //将剩下的一个盘子移动到目的塔上  
	      hanoi(n-1,denpend_on,from,to);//最后将借用塔上的n-1个盘子移动到目的塔上  
	    }  
	}  
	
	public void move(int n,int from,int to) //将编号为n的盘子由from移动到to  
	{
//		Zz ydzz=this.zze.get(from-1);
//		Zz mdzz=this.zze.get(to-1);
//		Pz pz=ydzz.pze.pop();
//		ydzz.pzn--;
//		mdzz.pze.push(pz);
//		mdzz.pzn++;
//		pz.zz=mdzz.zznu;
		Bz bz1=new Bz();
		bz1.from=from-1;
		bz1.to=to-1;
		bz1.pz=this.pzn-n;
		this.bz.add(bz1);
//		System.out.println("第"+bs+"步:将"+bz1.pz+"号盘子从"+bz1.from+"号柱子---->"+bz1.to+"号柱子");
//		for(int i=0;i<this.zze.size();i++)
//		{
//			System.out.println("第"+this.zze.get(i).zznu+"号盘子个数为："+this.zze.get(i).pze.size());
//		}
		this.bs++;
	}  

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int zz=3;
		Algorithm at=new Algorithm(zz);
		at.hanoi(zz, 1, 2, 3);
//		System.out.println("柱子个数为："+at.zze.size());
//		for(int i=0;i<at.zze.size();i++)
//		{
//			System.out.println("第"+at.zze.get(i).zznu+"号盘子个数为："+at.zze.get(i).pze.size());
//		}
	}
}

class Zz                                    //柱子类
{
	int pzn;                                //该柱子上的盘子个数
	int zznu;                               //柱子号
	int x;                                  //坐标x
	int y;                                  //坐标y
	Vector<Pz> pze;                         //该柱子上的盘子堆栈，只允许上端进出
	int heigh;                              //盘子堆叠起来的高度
	public void remove()                    //移除盘子
	{
//		System.out.println("第"+this.zznu+"号柱子有"+this.pze.size());
		this.pze.remove(this.pze.size()-1);
		this.csh();
	}
	public void add(Pz pz1)                 //增加盘子
	{
		this.pze.add(pz1);
		this.csh();
	}
	public void csh()
	{
		this.pzn=this.pze.size();
		this.heigh=this.y;
		for(int i=0;i<this.pzn;i++)
		{
			this.heigh=(int) (this.heigh-this.pze.get(i).h);
		}
	}
}

class Pz                                    //盘子类
{
	int pznu;                               //盘子号
	double l;                               //该盘子的长
	double h;                               //该盘子的宽
	int zz;                                 //所属的柱子
	int x;                                  //坐标x
	int y;                                  //坐标y
	Color color;
}

class Bz                                    //行动步骤
{
	int from;                               //源地柱子
	int to;                                 //目的柱子
	int pz;                                 //移动的盘子
}
