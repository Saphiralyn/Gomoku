import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;




public class kadai extends JPanel implements ActionListener{

	static BufferedImage reimu;
	static BufferedImage marisa;
	static BufferedImage banmen;
	static BufferedImage yukkuri;

	JButton b1 = new JButton("リスタート"); 

	static final int EMPTY = 0,BATSU=1, MARU =2;
	static final int YMAX =15,XMAX=15;
	ArrayList<Figure>figs = new ArrayList<Figure>();

	boolean turn = true;
	boolean newBanmen = false;
	int winner =EMPTY;
	int[][] board =new int[YMAX][XMAX];
	Text t1 = new Text(30,30,"",new Font("Serif",Font.BOLD,25));

	public kadai(){

		add(b1);
		b1.setBounds(100,50,350,30);
		b1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				winner=EMPTY;
				newBanmen = true;
				reset();
				//	  board[0][0]=0;(ここですべての枠をゼロにすることができるらしい。)
				//　ここで、盤面をrepaintしたい。

				for(int i=0;i<XMAX;i++){
					for(int j=0;j<YMAX;j++){
						board[j][i]=EMPTY;

					}
				} 
				repaint();
				
			}
		});


		/*
		 * addMouseListener(new MouseAdapter(){
		     public void mousePressed(MouseEvent evt){
		         pick(evt.getX(), evt.getY());
	        	  if(evt.getX()>=0&&evt.getX()<=960&&evt.getY()>=0&&evt.getY()<=100)
		        {
	        	//	  board[0][0]=0;(ここですべての枠をゼロにすることができるらしい。)
	        	//　ここで、盤面をrepaintしたい。
	        			Color toumei= new Color(255,255,255,0);
	      			tada.add(new Rect(toumei,1,1,1,1));

	        		  for(int i=0;i<XMAX;i++){
	        			  for(int j=0;j<YMAX;j++){
	        				  board[j][i]=EMPTY;
	        			  }
	        		  }
		        } 

		      			        }
		    });
		 */

		
		try{
			reimu=ImageIO.read(new File("image/reimu.png"));
			marisa=ImageIO.read(new File("image/marisa.png"));
			banmen=ImageIO.read(new File("image/banmen.png"));
			yukkuri=ImageIO.read(new File("image/yukkuri.jpg"));
		}
		catch(Exception ex){ }
		
		reset();

		setOpaque(false);
		addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent evt){
				Rect r=pick(evt.getX(),evt.getY());
				if(r==null || winner!=EMPTY){return;}
				int x=(r.getX()-80)/36,y=(r.getY()-100)/36;
				if(board[y][x]!=EMPTY){
					t1.setText("空いていません");repaint();return;
				}
				if(turn){
					figs.add(new Batsu(r.getX(),r.getY(),8));
					board[y][x]=BATSU;
				}else{
					figs.add(new Maru(r.getX(),r.getY(),10));
					board[y][x]=MARU;
				}
				int 
				        a = ck(x,y,1,1),b = ck(x,y,1,-1),
						c = ck(x,y,1,0),d = ck(x,y,0,1);
				
				if(a>4||b>4||c>4||d>4){
					t1.setText((turn?"黒":"赤")+"の勝利！");
					winner = turn? BATSU:MARU;
				}else{
					turn =!turn;
					t1.setText("次の手番："+(turn?"黒":"赤"));
				}
				repaint();
			}
		});

	}
	private void reset() {
		figs.clear();
		figs.add(t1);
		t1.txt = "五目並べ.次の手番：黒";
		turn = true;
		//figs.add(t1);
		Color toumei= new Color(255,255,255,0);
		for(int i=0;i<YMAX*XMAX;i++){

			int r = i/YMAX,c=i%YMAX;
			figs.add(new Rect(toumei,80+r*36,100+c*36,34,34));
		}
	}
	


	private int ck(int x,int y,int dx,int dy){
		int s = board[y][x],count=1;
		for(int i=1;ck1(x+dx*i,y+dy*i,s);++i){++count;}
		for(int i=1;ck1(x-dx*i,y-dy*i,s);++i){++count;}
		return count;

	}
	private boolean ck1(int x,int y,int s){
		return 0 <= x && x<XMAX && 0<=y&&y<YMAX&&board[y][x]==s;
	}

	static class Text implements Figure{
		int xpos,ypos;
		String txt;
		Font fn;
		public Text(int x, int y,String t,Font f){
			xpos=x;ypos=y;txt=t;fn=f;
		}
		public void setText(String t){txt=t;}
		public void draw(Graphics g){
			g.setColor(Color.BLACK);g.setFont(fn);
			g.drawString(txt, xpos, ypos);
		}
	}



	public static void main (String[] args){
		JFrame app =new JFrame("ゆっくり五つ目並び");
		JMenuBar menu= new JMenuBar();
		app.setJMenuBar(menu);
		JMenu m1= new JMenu("File"); 
		menu.add(m1);
		JMenuItem i2= new JMenuItem("About it");
		m1.add(i2);
		i2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt){
				JOptionPane.showMessageDialog(null, 
						"このゲーム（といえるかどうかわからな\nいけど）の素材は"
								+ "全部ネットにあるもの\nを使わせていただきました。まだAIも導入\nしてないし、音も出ません。始めて作り、とて\nもへたくそなプログラムですが、Javaのプロ\nグラミングにはいい経験になったと思っております。"
								+ "\nこれからもより複雑のプログラムを作りた思います。\n\nゲームVersion:0.00002");

			}

		});


		JMenuItem i3= new JMenuItem("About yukkuri");
		m1.add(i3);
		i3.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt){
				JOptionPane.showMessageDialog(null, "「ゆっくりしていってね!!!」は、2ちゃんねるなど\nの電子掲示板でよく用いられるアスキーアート\n (AA) によるキャラクターの一種、及びイン\nターネットスラングである。同人ゲームで\nある『東方Project』の主人公格のキャラク\nター「博麗霊夢」と「霧雨魔理沙」をモチーフにし\nたキャラクターが2人で「ゆっくりして\nいってね！！！」と歓迎の意を表す\n\n詳しくはこちらへ：http://ja.wikipedia.org/wiki/ゆっくりしていってね!!!");
			}

		});


		JMenuItem i1= new JMenuItem("Quit");
		m1.add(i1);
		i1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt){
				System.exit(0);
			}

		});

		app.add(new kadai());
		app.setSize(960,720);
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setVisible(true);



	}



	interface Figure {
		public void draw(Graphics g);
	}
	public Rect pick(int x, int y) { // ★ここはprivateとしてある!　公開しない!
		Rect r = null;
		for (Figure f : figs) {
			if (f instanceof Rect && ((Rect)f).hit(x,y))/*&&一度打ったところ？*/
			{
				r = (Rect)f;
			}
		}
		return r;
	}


	public void paintComponent(Graphics g){
		
			g.drawImage(banmen,61,81,null);	
			g.drawImage(yukkuri,600,80,null);
			for(Figure f : figs){
				f.draw(g);
			}
		


	}

	static abstract class SimpleFigure implements Figure {
		int xpos , ypos;
		int type = 0;
		public SimpleFigure(int x, int y){xpos = x; ypos =y;}
		public void moveTo(int x, int y){xpos = x; ypos = y;}
		public void draw(Graphics g){
			g.setColor(Color.black);
			((Graphics2D)g).setStroke(new BasicStroke(4));
		}
	}


	static class Maru extends SimpleFigure {
		int size;
		
		public Maru(int x, int y , int s){ super(x,y); size=s;type =1;}
		public void draw(Graphics g) {
			super.draw(g);
			g.drawImage(reimu,xpos-size-13,ypos-size-13,null);
		}
	}
	static class Batsu extends SimpleFigure {
		int size;
		public Batsu(int x, int y , int s){ super(x,y); size=s;type=1;}
		public void draw(Graphics g) {
			super.draw(g);
			g.drawImage(marisa,xpos-size-15,ypos-size-15,null);
		}
	}

	static class Rect extends SimpleFigure {
		Color col;
		int  width, height;

		public Rect(Color c, int x, int y, int w, int h) {
			super(x,y); col = c;width = w;height = h;
		}

		public boolean hit(int x, int y) {
			return xpos - width / 2 <= x && x <= xpos + width / 2
					&& ypos - height / 2 <= y && y <= ypos + height / 2;
			// 長方形の枠の中に入っていれば真
		}
		public int getX(){ return xpos;}
		public int getY(){ return ypos;}
		public void draw(Graphics g){
			g.setColor(col);
			g.fillRect(xpos-width/2, ypos-height/2, width, height);

		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
}
