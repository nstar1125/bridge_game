/*
 * 수정일 :	22.06.03
 * 작성자 :	김상민
 */

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class BGameView extends JFrame{
	private Container fLayer = getContentPane();			//fLayer : 프레임
	private TitlePanel tLayer;								//tLayer : 제목 화면
	private FileInPanel iLayer;								//iLayer : 파일선택 화면
	private LobbyPanel lLayer;								//lLayer : 로비 화면
	private ResultPanel rLayer;								//rLayer : 결과 화면
	private MapPanel mLayer;								//mLayer : 게임 화면
	private AnimalPanel[] pLayer = new AnimalPanel[4];			//pLayer : 플레이어 말 패널
	private AnimalStat[] statLayer = new AnimalStat[4];			//statLayer : 플레이어 상태 패널
	private CtrlPanel cLayer;									//cLayer : 컨트롤 패널
	
	public BGameView(){
		setTitle("Bridge Game");
		setSize(1440,820);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fLayer.setLayout(null);
	}
	public void buildUI() {		//UI세팅
		buildTitle();		//타이틀 생성
		buildFileIn();	//파일선택 생성
		buildLobby();		//로비 생성
		buildMap();		//맵 생성
		buildCtrl();		//컨트롤 패널 생성
		buildPlayers();	//플레이어 생성
		buildResult();	//결과화면 생성
		launch();			//setVisible
	}
	public void setFocus(String cmd) {				//화면 선택 함수
		switch(cmd) {
		case "title":						//제목화면 선택
			tLayer.setVisible(true);
			iLayer.setVisible(false);
			lLayer.setVisible(false);
			mLayer.setVisible(false);
			rLayer.setVisible(false);
			break;
		case "fileIn":						//파일선택화면 선택
			tLayer.setVisible(false);
			iLayer.setVisible(true);
			lLayer.setVisible(false);
			mLayer.setVisible(false);
			rLayer.setVisible(false);
			break;
		case "lobby":						//로비화면 선택
			tLayer.setVisible(false);
			iLayer.setVisible(false);
			lLayer.setVisible(true);
			mLayer.setVisible(false);
			rLayer.setVisible(false);
			break;
		case "stage":						//게임화면 선택
			tLayer.setVisible(false);
			iLayer.setVisible(false);
			lLayer.setVisible(false);
			mLayer.setVisible(true);
			rLayer.setVisible(false);
			break;
		case "result":						//결과화면 선택
			tLayer.setVisible(false);
			iLayer.setVisible(false);
			lLayer.setVisible(false);
			mLayer.setVisible(false);
			rLayer.setVisible(true);
			break;
		}
	}
	public void buildTitle() {					//타이틀 화면 생성
		tLayer = new TitlePanel();
		tLayer.setBounds(0,0,1440,800);
		tLayer.setLayout(null);
		fLayer.add(tLayer);				//프레임 위 추가
		
	}
	public void buildFileIn() {				//파일선택 화면 생성
		iLayer = new FileInPanel();
		iLayer.setOpaque(true);
		iLayer.setBackground(Color.red);
		iLayer.setBounds(0,0,1440,800);
		iLayer.setLayout(null);
		fLayer.add(iLayer);				//프레임 위 추가
	}
	public void buildLobby() {					//로비화면 생성
		lLayer = new LobbyPanel();
		lLayer.setBounds(0,0,1440,800);
		lLayer.setLayout(null);
		fLayer.add(lLayer);				//프레임 위 추가
	}
	public void buildMap(){					//게임화면 생성
		mLayer = new MapPanel();
		mLayer.setOpaque(false);
		mLayer.setBounds(0,0,1440,800);
		mLayer.setLayout(null);
		fLayer.add(mLayer);				//프레임 위 추가
	}
	public void addMap() {						//맵 그리기
		mLayer.setMap();
	}
	public void buildPlayers() {										//플레이어 생성
		pLayer[0] = new MousePanel(BGameModel.start);			//플레이어 말
		pLayer[1] = new PenguinPanel(BGameModel.start);
		pLayer[2] = new DogPanel(BGameModel.start);
		pLayer[3] = new LlamaPanel(BGameModel.start);
		
		statLayer[0] = new MouseStat(50,20);					//플레이어 상태
		statLayer[1] = new PenguinStat(50,20);
		statLayer[2] = new DogStat(50,20);
		statLayer[3] = new LlamaStat(50,20);
		
		
	}
	public void addPlayers(int pNum) {									//플레이어 수만큼 추가
		for(int i = 0; i<pNum; i++) {
			pLayer[i].setOpaque(false);
			pLayer[i].setBounds(0,0,800,800);
			mLayer.add(pLayer[i]);								//게임화면 위 추가
			
			statLayer[i].setLayout(null);
			statLayer[i].setOpaque(false);
			statLayer[i].setBounds(800,i*140,640,140);
			mLayer.add(statLayer[i]);							//게임화면 위 추가
		}
	}
	public void buildCtrl() {							//컨트롤 패널 생성
		cLayer = new CtrlPanel();
		cLayer.setOpaque(true);
		cLayer.setBounds(800,560,640,240);
		cLayer.setLayout(null);	
		mLayer.add(cLayer);						//게임화면 위 추가
		
	}
	public void buildResult() {							//결과화면 생성
		rLayer = new ResultPanel();
		rLayer.setBounds(0,0,1440,800);
		rLayer.setLayout(null);
		fLayer.add(rLayer);							//프레임 위 추가							
	}
	public void resultUpdate(int[] ary) {				//게임 결과 반영
		rLayer.setResult(ary);
	}
	public void launch() {								//setVisible
		setVisible(true);
	}
	public void pSetPos(int i, int n, int m) {			//플레이어 위치에 그리기
		pLayer[i].x = n;
		pLayer[i].y = m;
		for(int a = 0; a<4; a++) {				//플레이어 i 제일 앞으로 보이기
			mLayer.remove(pLayer[a]);
		}
		mLayer.add(pLayer[i]);
		for(int a = 0; a<4; a++) {
			if(a!=i) {
				mLayer.add(pLayer[a]);
			}
		}
		pLayer[i].repaint();
	}
	public void pSetPos(int i, Location l) {			//플레이어 위치에 그리기
		pLayer[i].x = l.x;
		pLayer[i].y = l.y;
		for(int a = 0; a<4; a++) {				//플레이어 i 제일 앞으로 보이기
			mLayer.remove(pLayer[a]);
		}
		mLayer.add(pLayer[i]);
		for(int a = 0; a<4; a++) {
			if(a!=i) {
				mLayer.add(pLayer[a]);
			}
		}
		pLayer[i].repaint();
	}
	public void statTurn(int i, boolean flag, boolean finish) {
		if(finish) {
			statLayer[i].setOpaque(true);
			statLayer[i].setBackground(Color.gray);
		}else {
			if(flag) {
				statLayer[i].setOpaque(true);
				statLayer[i].setBackground(Color.BLUE);
			}else {
				statLayer[i].setOpaque(false);
				statLayer[i].setBackground(null);
			}
		}
	}
	public void statUpdate(int i, Player p) {			//플레이어 상태 최신화
		statLayer[i].sUpdate(p);
	}
	public void cmdBtnEnable(boolean flag) {	//stay roll 버튼 활성화 함수
		cLayer.s_btn.setEnabled(flag);
		cLayer.r_btn.setEnabled(flag);
	}
	public void inputVisible(boolean flag) {	//input field & 이동 버튼 활성화 함수
		cLayer.aMove_label.setVisible(flag);
		cLayer.input_field.setVisible(flag);
		cLayer.isubmit_btn.setVisible(flag);
	}
	public void setSubmitBtnEnabled(boolean flag) {			//맵 파일 선택 버튼 활성화 함수
		iLayer.submit_btn.setEnabled(flag);
	}
	public void ctrlUpdate(String option, int n, boolean init) {	//컨트롤 패널 업데이트 (대상, 숫자, 초기화 여부)
		
		switch(option) {
		case "dice":
			if(init) {
				cLayer.dlbUpdate(0);
			}else {
				cLayer.dlbUpdate(n);
			}
			break;
		case "move":
			if(init) {
				cLayer.amlbUpdate();
			}else {
				cLayer.amlbUpdate(n);
			}
			break;
		default:
			break;	
		}
	}
	public void setFever(boolean flag) {					//피버타임 -> 컨트롤패널 색 변경
		if(flag) {
			cLayer.setBackground(Color.red);
		}else {
			cLayer.setBackground(Color.orange);
		}
	}
	public void showMsg(String cmd) {				//메시지 박스
		switch(cmd) {
		case "wrong_input":
			JOptionPane.showMessageDialog(null,"잘못된 입력입니다.","Invalid input!",JOptionPane.ERROR_MESSAGE);
			break;
		case "wrong_length":
			JOptionPane.showMessageDialog(null,"입력된 값이 가능한 움직임 수와 다릅니다.","Wrong input length!",JOptionPane.ERROR_MESSAGE);
			break;
		case "invalid_move":
			JOptionPane.showMessageDialog(null,"갈 수 없는 곳입니다.","Invalid move!",JOptionPane.ERROR_MESSAGE);
			break;
		}
		
	}
	public void setTextfieldText(String component, String txt) {		//텍스트필드 값 설정
		switch(component){
		case "fileIn_tf":
			iLayer.fileIn_tf.setText(txt);
			break;
		case "input_field":
			cLayer.input_field.setText(txt);
			break;
		}
	}
	public String getTextfieldText(String component) {				//텍스트필드 값 가져오기
		switch(component){
		case "fileIn_tf":
			return iLayer.fileIn_tf.getText();
		case "input_field":
			return cLayer.input_field.getText();
		default:
			return null;
		}
	}

}
class TitlePanel extends JPanel{								//타이틀 화면 패널
	private ImageIcon titleBg = new ImageIcon("images/title_bg.png");
	private Image title_img = titleBg.getImage();
	
	JButton start_btn;
	public TitlePanel(){
		start_btn = new JButton("start");					//시작 버튼
		SceneBtn_lnr stBlr = new SceneBtn_lnr(1);
		start_btn.addActionListener(stBlr);
		start_btn.setBounds(640,570,160,60);
		super.add(start_btn);
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(title_img, 0,0,1440,800,this);
	}
	
}
class FileInPanel extends JPanel{							//맵파일 선택 패널
	private ImageIcon finBg = new ImageIcon("images/fin_bg.png");
	private Image finBg_img = finBg.getImage();
	
	JLabel expl_lb;
	JButton open_btn, submit_btn;
	JTextField fileIn_tf;
	public FileInPanel(){
		expl_lb = new JLabel("맵 파일을 고른 다음 선택을 누르세요.");
		expl_lb.setBounds(640,350,350,30);
		super.add(expl_lb);
		
		fileIn_tf = new JTextField(30);
		Finfield_lnr finflr = new Finfield_lnr();
		fileIn_tf.addActionListener(finflr);
		fileIn_tf.setBounds(580,400,205,30);
		fileIn_tf.setEditable(false);
		super.add(fileIn_tf);
		
		open_btn = new JButton("찾기");
		OpenBtn_lnr oBlr = new OpenBtn_lnr();
		open_btn.addActionListener(oBlr);
		open_btn.setBounds(790,400,50,30);
		super.add(open_btn);
		
		submit_btn = new JButton("선택");
		SubmitBtn_lnr smBlr = new SubmitBtn_lnr(2);
		submit_btn.addActionListener(smBlr);
		submit_btn.setBounds(840,400,50,30);
		submit_btn.setEnabled(false);
		super.add(submit_btn);
		
		
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(finBg_img, 0,0,1440,800,this);
	}
}
class LobbyPanel extends JPanel{							//로비화면 패널
	private JButton two_btn, three_btn, four_btn;
	
	private ImageIcon lobbyBg = new ImageIcon("images/lobby.png");
	private Image lobby_img = lobbyBg.getImage();
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(lobby_img, 0,0,1440,800,this);
	}
	public LobbyPanel(){
		PnumBtn_lnr ccBlr = new PnumBtn_lnr(3);
		
		two_btn = new JButton("2");						//플레이어 수 버튼 3개
		two_btn.addActionListener(ccBlr);
		two_btn.setBounds(250,600,160,60);
		super.add(two_btn);
		
		three_btn = new JButton("3");
		three_btn.addActionListener(ccBlr);
		three_btn.setBounds(640,600,160,60);
		super.add(three_btn);
		
		four_btn = new JButton("4");
		four_btn.addActionListener(ccBlr);
		four_btn.setBounds(1050,600,160,60);
		super.add(four_btn);
	}
}

class MapPanel extends JPanel{									//게임화면(맵) 패널
	private ImageIcon tile = new ImageIcon("images/tile.png");
	private ImageIcon b_tile = new ImageIcon("images/b_tile.png");
	private ImageIcon j_tile = new ImageIcon("images/j_tile.png");
	private ImageIcon p_tile = new ImageIcon("images/p_tile.png");
	private ImageIcon s_tile = new ImageIcon("images/s_tile.png");
	private ImageIcon h_tile = new ImageIcon("images/h_tile.png");
	private ImageIcon st_tile = new ImageIcon("images/st_tile.png");
	private ImageIcon e_tile = new ImageIcon("images/e_tile.png");
	
	private Image tile_img = tile.getImage();
	private Image b_tile_img = b_tile.getImage();
	private Image j_tile_img = j_tile.getImage();
	private Image p_tile_img = p_tile.getImage();
	private Image s_tile_img = s_tile.getImage();
	private Image h_tile_img = h_tile.getImage();
	private Image st_tile_img = st_tile.getImage();
	private Image e_tile_img = e_tile.getImage();
	
	private ImageIcon mapBg = new ImageIcon("images/map_bg.png");
	private Image mapBg_img = mapBg.getImage();
	
	private int x=20;
	private int y=20;
	private int tLength = 57;
	private Cell[][] mapAry;	//맵 배열
	private int mR;				//맵 세로 길이
	private int mC;				//맵 가로 길이
	
	public MapPanel(){
		this.mapAry = null;
		this.mR = 0;
		this.mC = 0;
	}
	public void setMap() {
		this.mapAry = BGameModel.map;
		this.mR = BGameModel.mapR;
		this.mC = BGameModel.mapC;
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(mapBg_img,0,0,1440,800,this);
		for(int i = 0; i<mR; i++) {						//2차원 배열 반복
			for(int j = 0; j<mR; j++) {
				String tType = mapAry[i][j].type;
				if(tType!=null) {									//타입에 따라 다른 타일 그리기
					if(tType.equals("C")||tType.equals("b")){
						g.drawImage(tile_img,x+(tLength*j),y+(tLength*i),60,60,this);
					}else if(tType.equals("B")){
						g.drawImage(b_tile_img,x+(tLength*j),y+(tLength*i),60,60,this);
					}else if(tType.equals("J")){
						g.drawImage(j_tile_img,x+(tLength*j),y+(tLength*i),60,60,this);
					}else if(tType.equals("P")){
						g.drawImage(p_tile_img,x+(tLength*j),y+(tLength*i),60,60,this);
					}else if(tType.equals("S")){
						g.drawImage(s_tile_img,x+(tLength*j),y+(tLength*i),60,60,this);
					}else if(tType.equals("H")){
						g.drawImage(h_tile_img,x+(tLength*j),y+(tLength*i),60,60,this);
					}else if(tType.equals("St")){
						g.drawImage(st_tile_img,x+(tLength*j),y+(tLength*i),60,60,this);
					}else if(tType.equals("E")){
						g.drawImage(e_tile_img,x+(tLength*j),y+(tLength*i),60,60,this);
					}
				}
			}
		}
	}
	
}
class ResultPanel extends JPanel{									//결과화면
	private ImageIcon mouse_face = new ImageIcon("images/mouse_face.png");
	private Image mouse_face_img = mouse_face.getImage();
	private ImageIcon penguin_face = new ImageIcon("images/penguin_face.png");
	private Image penguin_face_img = penguin_face.getImage();
	private ImageIcon dog_face = new ImageIcon("images/dog_face.png");
	private Image dog_face_img = dog_face.getImage();
	private ImageIcon llama_face = new ImageIcon("images/llama_face.png");
	private Image llama_face_img = llama_face.getImage();
	
	private int[] result;
	private JLabel[] pScorelb;
	private JButton exitBtn;
	
	private ImageIcon resultBg = new ImageIcon("images/result_bg.png");
	private Image resultBg_img = resultBg.getImage();
	
	public ResultPanel(){
		pScorelb = new JLabel[4];
		exitBtn = new JButton("exit");
	}
	public void setResult(int[] ary){										//결과 설정
		result = ary;
		
		for(int i = 0; i<result.length; i++) {
			if(result[i]!=-1) {
				pScorelb[i] = new JLabel("player "+(i+1)+": "+result[i]+"점");		//결과 출력
				pScorelb[i].setBounds(720,200+i*100,200,50);
				super.add(pScorelb[i]);
			}
		}
		
		exitBtn.setBounds(680,600,80,30);
		super.add(exitBtn);											//나가기 버튼
		SceneBtn_lnr extBlr = new SceneBtn_lnr(-1);					//화면 번호 : -1 -> 종료
		exitBtn.addActionListener(extBlr);
		super.repaint();
		
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(resultBg_img,0,0,1440,800,this);
		if(result[0]!=-1) {
			g.drawImage(mouse_face_img,620,200,50,50,this);
		}
		if(result[1]!=-1) {
			g.drawImage(penguin_face_img,620,300,50,50,this);
		}
		if(result[2]!=-1) {
			g.drawImage(dog_face_img,620,400,50,50,this);
		}
		if(result[3]!=-1) {
			g.drawImage(llama_face_img,620,500,50,50,this);
		}
		
		
	}
}

class AnimalPanel extends JPanel{				//플레이어 말 패널
	protected int x;
	protected int y;
	
	public AnimalPanel(Location l){
		x = l.x;
		y = l.y;
	}
	public AnimalPanel(int a, int b){
		x = a;
		y = b;
	}
}

class MousePanel extends AnimalPanel{						//쥐 모양 말
	private ImageIcon mouse = new ImageIcon("images/mouse.png");
	private Image mouse_img = mouse.getImage();
	
	public MousePanel(Location l){
		super(l);
	}
	public MousePanel(int a, int b){
		super(a,b);
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(mouse_img,20+y*57,20+x*57,60,60,this);
	}
}

class PenguinPanel extends AnimalPanel{							//펭귄 모양 말
	private ImageIcon penguin = new ImageIcon("images/penguin.png");
	private Image penguin_img = penguin.getImage();
	
	public PenguinPanel(Location l){
		super(l);
	}
	public PenguinPanel(int a, int b){
		super(a,b);
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(penguin_img,20+y*57,20+x*57,60,60,this);
	}
}

class DogPanel extends AnimalPanel{								//개 모양 말
	private ImageIcon dog = new ImageIcon("images/dog.png");
	private Image dog_img = dog.getImage();
	public DogPanel(Location l){
		super(l);
	}
	public DogPanel(int a, int b){
		super(a,b);
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(dog_img,20+y*57,20+x*57,60,60,this);
	}
}

class LlamaPanel extends AnimalPanel{							//라마 모양 말
	private ImageIcon llama = new ImageIcon("images/llama.png");
	private Image llama_img = llama.getImage();
	public LlamaPanel(Location l){
		super(l);
	}
	public LlamaPanel(int a, int b){
		super(a,b);
	}	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(llama_img,20+y*57,20+x*57,60,60,this);
	}
}

class AnimalStat extends JPanel{								//플레이어 상태 패널
	
	protected ImageIcon b_card_icn = new ImageIcon("images/b_card.png");
	protected ImageIcon p_card_icn = new ImageIcon("images/p_card.png");
	protected ImageIcon s_card_icn = new ImageIcon("images/s_card.png");
	protected ImageIcon h_card_icn = new ImageIcon("images/h_card.png");
	
	protected Image b_card_img = b_card_icn.getImage();
	protected Image p_card_img = p_card_icn.getImage();
	protected Image h_card_img = h_card_icn.getImage();
	protected Image s_card_img = s_card_icn.getImage();
	
	protected JLabel b_c_label;
	protected JLabel p_c_label;
	protected JLabel s_c_label;
	protected JLabel h_c_label;
	
	protected int space;
	protected int stat_x;
	protected int stat_y;
	
	public void sUpdate(Player p) {
		b_c_label.setText("x"+p.deck.b_card);
		p_c_label.setText("x"+p.deck.p_card);
		s_c_label.setText("x"+p.deck.s_card);
		h_c_label.setText("x"+p.deck.h_card);
	}
	public AnimalStat(int x, int y){
		
		stat_x = x;
		stat_y = y;
		space = 100;
		
		b_c_label = new JLabel("x0");
		p_c_label = new JLabel("x0");
		s_c_label = new JLabel("x0");
		h_c_label = new JLabel("x0");
		
		int temp_x = stat_x+space+30;
		b_c_label.setBounds(temp_x+space*0,stat_y+60,80,50);
		b_c_label.setHorizontalAlignment(JLabel.CENTER);
		p_c_label.setBounds(temp_x+space*1,stat_y+60,80,50);
		p_c_label.setHorizontalAlignment(JLabel.CENTER);
		s_c_label.setBounds(temp_x+space*2,stat_y+60,80,50);
		s_c_label.setHorizontalAlignment(JLabel.CENTER);
		h_c_label.setBounds(temp_x+space*3,stat_y+60,80,50);
		h_c_label.setHorizontalAlignment(JLabel.CENTER);
		
		super.add(b_c_label);
		super.add(p_c_label);
		super.add(s_c_label);
		super.add(h_c_label);
		
		

	}
}

class MouseStat extends AnimalStat{									//쥐 플레이어 상태
	private ImageIcon mouse_face = new ImageIcon("images/mouse_face.png");
	private Image mouse_face_img = mouse_face.getImage();
	
	public MouseStat(int a, int b){
		super(a, b);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		int temp_x = stat_x+space+30;
		g.drawImage(mouse_face_img,stat_x,stat_y,100,100,this);
		g.drawImage(b_card_img,temp_x+space*0,stat_y,80,80,this);
		g.drawImage(p_card_img,temp_x+space*1,stat_y,80,80,this);
		g.drawImage(s_card_img,temp_x+space*2,stat_y,80,80,this);
		g.drawImage(h_card_img,temp_x+space*3,stat_y,80,80,this);
		
	}
	
}

class PenguinStat extends AnimalStat{										//펭귄 플레이 상태
	private ImageIcon penguin_face = new ImageIcon("images/penguin_face.png");
	private Image penguin_face_img = penguin_face.getImage();
	
	public PenguinStat(int a, int b){
		super(a, b);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		int temp_x = stat_x+space+30;
		g.drawImage(penguin_face_img,stat_x,stat_y,100,100,this);
		g.drawImage(b_card_img,temp_x+space*0,stat_y,80,80,this);
		g.drawImage(p_card_img,temp_x+space*1,stat_y,80,80,this);
		g.drawImage(s_card_img,temp_x+space*2,stat_y,80,80,this);
		g.drawImage(h_card_img,temp_x+space*3,stat_y,80,80,this);
		
	}
	
}

class DogStat extends AnimalStat{									//개 플레이어 상태
	private ImageIcon dog_face = new ImageIcon("images/dog_face.png");
	private Image dog_face_img =dog_face.getImage();
	
	public DogStat(int a, int b){
		super(a, b);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		int temp_x = stat_x+space+30;
		g.drawImage(dog_face_img,stat_x,stat_y,100,100,this);
		g.drawImage(b_card_img,temp_x+space*0,stat_y,80,80,this);
		g.drawImage(p_card_img,temp_x+space*1,stat_y,80,80,this);
		g.drawImage(s_card_img,temp_x+space*2,stat_y,80,80,this);
		g.drawImage(h_card_img,temp_x+space*3,stat_y,80,80,this);
		
	}
	
}

class LlamaStat extends AnimalStat{										//라마 플레이어 상태
	private ImageIcon llama_face = new ImageIcon("images/llama_face.png");
	private Image llama_face_img = llama_face.getImage();
	
	public LlamaStat(int a, int b){
		super(a, b);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		int temp_x = stat_x+space+30;
		g.drawImage(llama_face_img,stat_x,stat_y,100,100,this);
		g.drawImage(b_card_img,temp_x+space*0,stat_y,80,80,this);
		g.drawImage(p_card_img,temp_x+space*1,stat_y,80,80,this);
		g.drawImage(s_card_img,temp_x+space*2,stat_y,80,80,this);
		g.drawImage(h_card_img,temp_x+space*3,stat_y,80,80,this);
		
	}
	
}

class CtrlPanel extends JPanel{									//컨트롤 패널
	
	private ImageIcon dice_1 = new ImageIcon("images/dice_1.png");
	private ImageIcon dice_2 = new ImageIcon("images/dice_2.png");
	private ImageIcon dice_3 = new ImageIcon("images/dice_3.png");
	private ImageIcon dice_4 = new ImageIcon("images/dice_4.png");
	private ImageIcon dice_5 = new ImageIcon("images/dice_5.png");
	private ImageIcon dice_6 = new ImageIcon("images/dice_6.png");
	private ImageIcon dice_w = new ImageIcon("images/dice_wait.png");
	
	private Image dice_1_img =dice_1.getImage();
	private Image dice_2_img =dice_2.getImage();
	private Image dice_3_img =dice_3.getImage();
	private Image dice_4_img =dice_4.getImage();
	private Image dice_5_img =dice_5.getImage();
	private Image dice_6_img =dice_6.getImage();
	private Image dice_w_img =dice_w.getImage();
	
	private int dice_num;
	
	JButton s_btn;			//Stay 버튼
	JButton r_btn;			//Roll 버튼
	JLabel aMove_label;		//가능한 움직임수 라벨
	JTextField input_field;	//input 텍스트 필드
	JButton isubmit_btn;	//input submit 버튼
	
	public CtrlPanel(){
		dice_num = 0;
		
		s_btn = new JButton("Stay");				//Stay 버튼 추가
		CmdBtn_lnr sBlr = new CmdBtn_lnr("stay");
		s_btn.addActionListener(sBlr);
		s_btn.setBounds(20,160,80,30);
		super.add(s_btn);
		
		r_btn = new JButton("Roll");				//Roll 버튼 추가
		CmdBtn_lnr rBlr = new CmdBtn_lnr("roll");
		r_btn.addActionListener(rBlr);
		r_btn.setBounds(100,160,80,30);
		super.add(r_btn);
		
		aMove_label = new JLabel("input :");		//가능움직임 수 라벨 추가
		aMove_label.setBounds(200,160,80,30);
		aMove_label.setVisible(false);
		super.add(aMove_label);
		
		input_field = new JTextField(30);			//input 텍스트필드 추가
		Ifield_lnr iflr = new Ifield_lnr();
		input_field.addActionListener(iflr);
		input_field.setBounds(260,160,300,30);
		input_field.setVisible(false);
		super.add(input_field);
		
		isubmit_btn = new JButton("이동");			//input_submit 버튼 추가
		ISubmit_lnr isBlr = new ISubmit_lnr();
		isubmit_btn.addActionListener(isBlr);
		isubmit_btn.setBounds(560,160,50,30);
		isubmit_btn.setVisible(false);
		super.add(isubmit_btn);
	}
	public void dlbUpdate(int d_n) {		//주사위 수 업데이트
		dice_num = d_n;
	}
	public void amlbUpdate() {							//가능움직임 수 초기화
		aMove_label.setText("input : ");
	}
	public void amlbUpdate(int am_n) {					//가능움직임 수
		aMove_label.setText("input("+Integer.toString(am_n)+") : ");
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		switch(dice_num) {
		case 0:
			g.drawImage(dice_w_img,50,50,100,100,this);
			break;
		case 1:
			g.drawImage(dice_1_img,50,50,100,100,this);
			break;
		case 2:
			g.drawImage(dice_2_img,50,50,100,100,this);
			break;
		case 3:
			g.drawImage(dice_3_img,50,50,100,100,this);
			break;
		case 4:
			g.drawImage(dice_4_img,50,50,100,100,this);
			break;
		case 5:
			g.drawImage(dice_5_img,50,50,100,100,this);
			break;
		case 6:
			g.drawImage(dice_6_img,50,50,100,100,this);
			break;
		}
		super.repaint();
	}
}
class OpenBtn_lnr implements ActionListener{	//파일 찾기 버튼
	private JFileChooser chooser;
	public OpenBtn_lnr(){
		chooser = new JFileChooser();
	}
	public void actionPerformed(ActionEvent e) {
		FileNameExtensionFilter filter = new FileNameExtensionFilter("MAP Files","map");
		chooser.setFileFilter(filter);
		chooser.setCurrentDirectory(new File("map"));
		chooser.showOpenDialog(null);
		String filePath = chooser.getSelectedFile().getPath();
		BGameController.gView.setTextfieldText("fileIn_tf", filePath);
		BGameController.gView.setSubmitBtnEnabled(true);
	}
}
class SceneBtn_lnr implements ActionListener{	//화면 전환 버튼 리스너
	private int sceneNum;
	public SceneBtn_lnr(int n){
		sceneNum = n;
	}
	public void actionPerformed(ActionEvent e) {
		BGameController.setScene(sceneNum);		//클릭 시, 화면 번호 설정
		
	}
}
class PnumBtn_lnr extends SceneBtn_lnr{			//플레이어 수 설정 버튼 리스너
	public PnumBtn_lnr(int n){
		super(n);
	}
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);				//클릭 시, 화면 번호 설정
		JButton temp = (JButton)e.getSource();
		if(temp.getText().equals("2")) {		//버튼에 2 적혀있으면 플레이어 수 -> 2
			BGameModel.playersNum = 2;
		}else if(temp.getText().equals("3")) {	//버튼에 3 적혀있으면 플레이어 수 -> 3
			BGameModel.playersNum = 3;
		}else if(temp.getText().equals("4")) {	//버튼에 4 적혀있으면 플레이어 수 -> 4
			BGameModel.playersNum = 4;
		}
	}
}

class SubmitBtn_lnr extends SceneBtn_lnr{		//파일 입력 버튼 리스너
	public SubmitBtn_lnr(int n){
		super(n);
	}
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);				//클릭 시, 화면 번호 설정
		BGameController.setFileName(BGameController.gView.getTextfieldText("fileIn_tf"));	//텍스트필드 값 파일이름으로 저장
		BGameController.gView.setTextfieldText("fileIn_tf",null);
	}
}
class Finfield_lnr implements ActionListener{		//파일 입력 텍스트 필드 리스너 (엔터도 저장)
	public void actionPerformed(ActionEvent e) {
		BGameController.setFileName(BGameController.gView.getTextfieldText("fileIn_tf"));
		BGameController.gView.setTextfieldText("fileIn_tf",null);
		BGameController.setScene(2);				//로비화면 전환
	}
}

class CmdBtn_lnr implements ActionListener{			//커맨드 버튼 리스너 
	private String cmd;
	public CmdBtn_lnr(String str){
		cmd = str;
	}
	public void actionPerformed(ActionEvent e) {	//클릭 시, 커맨드 설정
		BGameController.setCmd(cmd);
	}
}
class ISubmit_lnr implements ActionListener{			//input submit 버튼
	public void actionPerformed(ActionEvent e) {		//버튼 누르면 input 설정
		BGameController.setInputStr(BGameController.gView.getTextfieldText("input_field"));
		BGameController.gView.setTextfieldText("input_field",null);
	}
}
class Ifield_lnr implements ActionListener{				//input 필드 리스너
	public void actionPerformed(ActionEvent e) {		//엔터 누르면 input 설정
		BGameController.setInputStr(BGameController.gView.getTextfieldText("input_field"));
		BGameController.gView.setTextfieldText("input_field",null);
	}
}

