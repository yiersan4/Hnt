package hnttest;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class View extends JDialog implements ActionListener,Runnable{

	JLabel jl1,jl2,jl3;
	JTextField jtf1,jtf2,jtf3;
	JButton jCon,jStop,jCancel;
	Algorithm at;
	Thread t;
	
	public View()
	{
		jl1=new JLabel("盘子个数：");
		jl1.setBounds(30, 450, 80, 30);
		this.add(jl1);
		
		jl2=new JLabel("移动速度：");
		jl2.setBounds(30, 420, 80, 30);
		this.add(jl2);
		
		jl3=new JLabel("缓冲时间：");
		jl3.setBounds(30, 390, 80, 30);
		this.add(jl3);
		
		jtf1=new JTextField(20);
		jtf1.setBounds(100, 450, 80, 30);
		jtf1.setText("3");                             //设置默认值
		this.add(jtf1);
		
		jtf2=new JTextField(20);
		jtf2.setBounds(100, 420, 80, 30);
		jtf2.setText("1");                            //设置默认值
		this.add(jtf2);
		
		jtf3=new JTextField(20);
		jtf3.setBounds(100, 390, 80, 30);
		jtf3.setText("1");                            //设置默认值
		this.add(jtf3);
		
		jCon=new JButton("开始");
		jCon.setBounds(220,450,70,30);
		this.add(jCon);
		jCon.addActionListener(this);
		
		jStop=new JButton("暂停");
		jStop.setBounds(300,450,70,30);
		this.add(jStop);
		jStop.addActionListener(this);
		
		jCancel=new JButton("退出");
		jCancel.setBounds(380,450,70,30);
		this.add(jCancel);
		jCancel.addActionListener(this);
		
		at=new Algorithm(3);
		
		//空布局
		this.setLayout(null);
		//不使用上下框
		this.setUndecorated(true);
		this.setSize(700,500);
		this.setLocation(0,0);
		this.setVisible(true);
		
	}
	
	public void paint(Graphics g)                       //覆盖JPanel的paint方法，屏幕显示时候自动调用一次，Graphics是绘图的重要类，相当于一只画笔
	{
		//调用父类函数完成初始化   
		
		super.paint(g);                                 //不能少,作用：清空、初始化、重置
		this.drawzz(g);
		this.drawpz(g);
	}
	
	public void drawzz(Graphics g)                      //画柱子
	{
		if(this.at!=null)
		{
			for(int i=0;i<this.at.zzn;i++)
			{
				int x=at.zze.get(i).x;
				int y=at.zze.get(i).y;
				g.setColor(Color.black);
				g.drawLine((int)(x-Data.zzl/2), y, (int)(x+Data.zzl/2), y);
//				g.fillOval(x, y, (int) Data.zzl, (int) (Data.zzl*Data.bl));
//				g.drawLine((int)(x+x+Data.zzl)/2, y, (int)(x+x+Data.zzl)/2, y-Data.zg);
				
			}
		}
	}
	
	public void drawpz(Graphics g)                      //画盘子
	{
		if(this.at!=null)
		{
			for(int i=0;i<this.at.zzn;i++)
			{
				for(int j=0;j<this.at.zze.get(i).pze.size();j++)
				{
					Pz pz1=this.at.zze.get(i).pze.get(j);
					int width=(int) pz1.l;
					int heigh=(int) pz1.h;
					int x=pz1.x-width/2;
					int y=pz1.y-heigh/2;
					g.setColor(pz1.color);
					g.fill3DRect(x, y, width, heigh, false);
					
				}		
			}
		}
	}
	
	public void move()                                 //画移动过程
	{
		
		for(int i=0;i<this.at.bz.size();i++)
		{
			System.out.print("共"+this.at.bz.size()+"步，现在是第"+(i+1)+"步：");
			Bz bz1=this.at.bz.get(i);
			Zz zzf=this.at.zze.get(bz1.from);
			Zz zzt=this.at.zze.get(bz1.to);
			Pz pz=this.at.pze.get(bz1.pz);
			System.out.println("将"+bz1.pz+"号盘子从"+bz1.from+"号柱子---->"+bz1.to+"号柱子");
			int heighmax=this.at.zze.get(0).heigh;
			for(int j=0;j<this.at.zze.size();j++)
			{
				int heigh=this.at.zze.get(j).heigh;
				if(heighmax>heigh)heighmax=heigh;
			}
//			System.out.println(heighmax);
			while(pz.y>(int)(heighmax-pz.h/2-5))
			{
				if(Math.abs(pz.x-(heighmax-pz.h/2-5))<=Data.speed)
				{
					pz.y=(int)(heighmax-pz.h/2-5);
				}else
				{
					pz.y=pz.y-Data.speed;
				}
				this.repaint();
				try {
					Thread.sleep(Data.hc);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			while(pz.x!=zzt.x)
			{
				if(Math.abs(pz.x-zzt.x)<=Data.speed)
				{
					pz.x=zzt.x;
				}else
				{
					if(pz.x<zzt.x)
					{
						pz.x=pz.x+Data.speed;
					}else
					{
						pz.x=pz.x-Data.speed;
					}
				}
				this.repaint();
				try {
					Thread.sleep(Data.hc);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			while(pz.y!=(int) (zzt.heigh-pz.h/2))
			{
				if(Math.abs(pz.y-zzt.heigh+pz.h/2)<=Data.speed)
				{
					pz.y=(int) (zzt.heigh-pz.h/2);
				}else
				{
					pz.y=pz.y+Data.speed;
				}
				this.repaint();
				try {
					Thread.sleep(Data.hc);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
//			System.out.println("到");
			zzf.remove();
			zzt.add(pz);
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		View view=new View();
		
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		this.move();
	}


	@Override
	public void actionPerformed(ActionEvent ee) {
		// TODO Auto-generated method stub
		if(ee.getSource()==jCancel)       //退出
		{
			this.dispose();
			System.exit(0);
		}else if(ee.getSource()==jCon)    //开始
		{
			int pz=Integer.valueOf(this.jtf1.getText().trim());
			int speed=Integer.valueOf(this.jtf2.getText().trim());
			int hc=Integer.valueOf(this.jtf3.getText().trim());
			Data.pz=pz;
			Data.speed=speed;
			Data.hc=hc;
			this.at=new Algorithm(pz);
			at.hanoi(pz, 1, 2, 3);
			this.repaint();
			this.t=new Thread(this);
			t.start();
			this.jStop.setText("暂停");
		}else if(ee.getSource()==jStop)   //暂停和恢复
		{

			if(this.t!=null)
			{
				if(this.jStop.getText().equals("暂停"))
				{
					this.t.suspend();
					this.jStop.setText("继续");
				}else if(this.jStop.getText().equals("继续"))
				{
					int speed=Integer.valueOf(this.jtf2.getText().trim());
					int hc=Integer.valueOf(this.jtf3.getText().trim());
					Data.speed=speed;
					Data.hc=hc;
					this.t.resume();
					this.jStop.setText("暂停");
				}
			}
		}
	}
}
