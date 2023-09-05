/*
 * 수정일 :	22.06.03
 * 작성자 :	김상민
 */


import java.io.*;
import java.util.*;


public class BGameController {
	
	private static int sceneNum;		//화면전환용 변수
	private static String fName;		//파일 이름
	private static String cmdStr;		//커맨드 명령(stay or roll)
	private static String inputStr;		//입력 명령(ex. UUDRR)
	static BGameView gView;		
	
	public BGameController(BGameView v){
		sceneNum = 0;
		fName = null;
		cmdStr = null;
		inputStr = null;
		gView = v;
		
		setUI();			//UI 세팅
		int scene = 0;
		do {
			scene = BGameController.getScene();
			switch(scene) {
			case 0:							//타이틀 화면
				gView.setFocus("title");
				break;
			case 1:							//파일선택 화면
				gView.setFocus("fileIn");
				break;
			case 2:							//로비 화면
				gView.setFocus("lobby");
				break;
			case 3:
				initialize();				//맵 파일에서 받아오기
				setMap();					//맵 설정
				setPlayers();				//플레이어 수만큼 설정
				gView.setFocus("stage");	//게임 화면
				gamePlay();					//게임 플레이
				BGameController.setScene(4);		//결과화면 전환
				break;
			case 4:
				gView.setFocus("result");		//결과 화면
				showResult();					//결과 출력
				break;
			}
			try {
				Thread.sleep(10);
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
		}while(scene!=-1);
		
		System.exit(0);			//프로그램 종료
			
	}
	public static void setUI() {		//UI세팅
		gView.buildUI();
	}
	public static void initialize() {		//초기화 설정
		File file = null;
		try {
			file = new File(BGameController.getFileName());		//파일 다운
			BufferedReader fin = new BufferedReader(new FileReader(file));
			String input_stream = "";
			ArrayList<String> dataList = new ArrayList<String>();
			while((input_stream = fin.readLine())!=null) {
				dataList.add(input_stream);
			}
			makeMap(dataList);		//dataList로 맵 설정
			fin.close();
		}catch (IOException e){
			System.out.println("IOException has occured !!");
		}
	}
	public static void makeMap(ArrayList<String> list){
		for(int x=0;x<BGameModel.map.length;x++) {			//맵 객체 생성
			for(int y=0;y<BGameModel.map[x].length;y++) {
				BGameModel.map[x][y] = new Cell();
			}
		}	
		int i = 0, j = 0;
		Location s = new Location();
		
		for(int n = 0; n<list.size(); n++) {			//dataList 크기만큼 반복
			String[] ary = list.get(n).split(" ");
			BGameModel.map[i][j].type = ary[0]; 		//첫번째 글자 : 셀의 종류
			if(ary[0].equals("S")) {					//첫번째 글자 = S이면
				if(ary.length == 2) {						//ary 길이가 2이면 : 셀은 Start
					BGameModel.map[i][j].type = "St";		
					BGameModel.map[i][j].pDir = "X";
					BGameModel.map[i][j].nDir = "R";  
				}else {										//ary길이가 2가 아니면(3) : 셀은 Saw
					BGameModel.map[i][j].pDir = ary[1]; 
					BGameModel.map[i][j].nDir = ary[2]; 
				}
			}else if(ary[0].equals("E")) {				//첫번째 글자 = E이면 : end
				BGameModel.map[i][j].pDir = "X";
				BGameModel.map[i][j].nDir = "X";
			}else if(ary[0].equals("B")){				//첫번째 글자 = B이면 : Bridge 시작
				BGameModel.map[i][j].pDir = ary[1]; 
				BGameModel.map[i][j].nDir = ary[2];
				BGameModel.map[i][j+1].type = "J";			//오른쪽 셀 (J L R)로 설정
				BGameModel.map[i][j+1].pDir = "L"; 
				BGameModel.map[i][j+1].nDir = "R";
			}else {
				BGameModel.map[i][j].pDir = ary[1]; 	//첫번째 글자 = C, P, H, b 등 나머지이면 : 그대로
				BGameModel.map[i][j].nDir = ary[2];
			}
			switch(BGameModel.map[i][j].nDir) {	//셀의 다음 방향이
			case "U":		//U이면 행-1
				i-=1;
				break;
			case "D":		//D이면 행+1
				i+=1;
				break;
			case "L":		//L이면 열-1
				j-=1;
				break;
			case "R":		//R이면 열+1
				j+=1;
				break;
			case "X":		//X이면 end
				break;
			default:
				break;		
			}
			if(i<0) {		//-1번 행에 추가할 때
				i++;		// i: -1 -> 0
				s.x++;		//start x 1 증가
				for(int x = BGameModel.map.length-1; x > 0; x--) {	//맵 전체 행+1
					BGameModel.map[x] = BGameModel.map[x-1];
				}
				BGameModel.map[0] = new Cell[BGameModel.map.length];
				for(int y = 0; y<BGameModel.map.length; y++)		//맵 0번 행 추가
					BGameModel.map[0][y] = new Cell();
				
			}
			if(j<0) {		//-1번 열에 추가 할 때
				j++;		// j: -1 -> 0
				s.y++;		//start y 1 증가
				for(int x = 0; x < BGameModel.map.length; x++) {
					for(int y = BGameModel.map[x].length - 1; y > 0; y--) {		//맵 전체 열+1
						BGameModel.map[x][y] = BGameModel.map[x][y-1];
					}
					BGameModel.map[x][0] = new Cell();		//맵 0번 열 추가
				}
			}	
		}								//맵 2차원 배열에 저장 성공
		BGameModel.start = s;			//start 위치 저장
		int max_x = 0, max_y = 0;
		for(int x = 0; x < BGameModel.map.length; x++) {
			for(int y = 0; y < BGameModel.map.length; y++) {
				if(BGameModel.map[x][y].type!=null) {			//맵 실제 크기 계산
					if(max_x<x)
						max_x = x;
					if(max_y<y)
						max_y = y;
				}
			}
		}
		BGameModel.mapR = max_x+1;		//맵 실제 행 길이 저장
		BGameModel.mapC = max_y+1;		//맵 실제 열 길이 저장
		
	}
	public static void setMap() {		//맵 그리기
		gView.addMap();
	}
	public static void setPlayers() {			//플레이어 설정
		BGameModel.player = new Player[BGameModel.playersNum];		//플레이어 숫자만큼 플레이어 객체 생성
		for(int i = 0; i < BGameModel.playersNum; i++) {
			BGameModel.player[i] = new Player(i);		//i번 플레이어 생성	
			BGameModel.player[i].setLoc(BGameModel.start.x, BGameModel.start.y);	//플레이어 위치 start위치로
			gView.pSetPos(BGameModel.player[i].id, BGameModel.player[i].curLoc);	//플레이어 위치 시작위치에 다시 그리기
		}
		gView.addPlayers(BGameModel.playersNum);		//플레이어 말과 플레이어 상태창 화면에 추가
	}
	public static void gamePlay() {			//게임 플레이 시작
		int i = 0;						//플레이어 턴
		boolean feverTime = false;		//피버타임 off
		gView.setFever(feverTime);		//피버타임 표시
		ArrayList<Player> pInlist = new ArrayList<Player>(); //플레이어가 들어온 순서 리스트 생성
		while(pInlist.size()<BGameModel.player.length-1) {			//한명 빼고 모두 들어올때까지 반복
			if(!BGameModel.player[i].finish) {									//플레이어i가 완주 아직 안했을 때,
				gView.statTurn(i,true,BGameModel.player[i].finish);				//플레이어i 상태창 파란색
				turn(BGameModel.player[i], feverTime);							//플레이어i 턴 진행
				gView.statTurn(i,false,BGameModel.player[i].finish);			//플레이어i 상태창 원래대로
				if(BGameModel.player[i].finish) {
					pInlist.add(BGameModel.player[i]);							//플레이어i 완주하면 pInlist에 추가
				}
				i++;
			}else {			//플레이어i가 완주 했으면, 다음 플레이어
				i++;
			}
			if(pInlist.size()>0) {		//만약 한명 이상 들어오면 피버타임 on
				feverTime = true;
				gView.setFever(feverTime);		//피버타임 표시
			}
			if(i == BGameModel.player.length)		//마지막 플레이어였으면 다시 처음 플레이어로
				i = 0;
		}								//한명 빼고 모두 들어옴
		for(int a = 0; a<3; a++) {			//들어온 순서대로 순위 부여 (최대 3등)		
			if(a<BGameModel.playersNum-1) {
				pInlist.get(a).rank = a+1;
			}else {
				break;
			}
		}
	}
	public static void turn(Player p, boolean fever) {
		gView.pSetPos(p.id, p.curLoc);				//플레이어 제일 위로 보이기
		String command;
		do {
			command = BGameController.getCmd();		//커맨드 들어올때까지 대기
			try {
				Thread.sleep(10);
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
		}while(command==null);
		BGameController.cmdBtnEnable(false);		//stay, roll 버튼 비활성화
		BGameController.inputVisible(true);			//input_field & submit 버튼 보이기
		if(command.equals("stay")) {				//커맨드 : stay이면
			BGameController.setCmd(null);			//커맨드 초기화
			
			if(p.deck.b_card>0)						//다리카드 -1
				p.deck.b_card--;
		}else if(command.equals("roll")) {					//커맨드 : roll이면
			BGameController.setCmd(null);					//커맨드 초기화
			int diceNum = (int)(Math.random() * 6) + 1;					//주사위 랜덤숫자(1~6)설정
			gView.ctrlUpdate("dice",diceNum, false);					//컨트롤 패널 업데이트 (대상, 숫자, 초기화 여부) = (주사위, 주사위숫자, 초기화x)
			gView.ctrlUpdate("move",diceNum-p.deck.b_card, false);		//컨트롤 패널 업데이트 (대상, 숫자, 초기화 여부) = (움직임수, 주사위-다리카드, 초기화x)
			
			boolean validMove = false;
			while(!validMove) {				//valid move일때까지 반복
				String input;
				Location sLoc = new Location(p.curLoc.x, p.curLoc.y);		//플레이어 현재 위치 -> sLoc(시작 위치)
				do {									//valid move일때까지 반복
					if(diceNum-p.deck.b_card>0) {			//만약 가능 움직임 수가 0보다 클 때
						do {
							input = BGameController.getInputStr();		//input 받을 때까지 대기
							try {
								Thread.sleep(10);
							}catch(InterruptedException e) {
								e.printStackTrace();
							}
						}while(input==null);
					}else {									//만약 가능 움직임 수가 0 이하면, input은 ""
						input = "";
					}
					validMove = true;
					if(input.length() == diceNum-p.deck.b_card || diceNum-p.deck.b_card < 0) {		//만약 input길이가 가능 움직임 수랑 같거나 0이하면
						for(int i = 0; i < input.length(); i++) {									//i번째 글자가 udlrUDLR중 하나라도 아니면 valid move는 false
							if((input.charAt(i)!='u')&&(input.charAt(i)!='U')&&(input.charAt(i)!='d')&&(input.charAt(i)!='D')&&(input.charAt(i)!='l')&&(input.charAt(i)!='L')&&(input.charAt(i)!='r')&&(input.charAt(i)!='R')) {
								BGameController.setInputStr(null);
								gView.showMsg("wrong_input");		//잘못된 입력 오류 메시지 출력
								validMove = false;
								break;
							}
						}
					}else {									//input 길이가 다름
						BGameController.setInputStr(null);
						gView.showMsg("wrong_length");		//잘못된 길이 오류 메시지 출력
						validMove = false;
					}
				}while(!validMove);		//invalid input이면 반복
				
				boolean cross_b = false ;	//다리 건넘 여부 : false
				
				for(int i = 0; i<input.length(); i++) {		//input 길이 반복
					boolean fbackward = false;			//뒤로 이동 여부 : false
					if(fever && Character.toUpperCase(input.charAt(i))==BGameModel.map[p.curLoc.x][p.curLoc.y].pDir.charAt(0)) { //피버타임에 현재 셀에서 뒷방향으로 이동하려하면
						fbackward = true;		//뒤로 이동 여부 : true
					}
					
					if(BGameModel.map[p.curLoc.x][p.curLoc.y].type.equals("J")&&(input.charAt(i)=='R' || input.charAt(i)=='r')) {	//다리위에서 오른쪽으로 이동 
						cross_b = true;		//다리 건넘 여부 : true
					}
					
					try {
						Thread.sleep(100);
					}catch(InterruptedException e) {
						e.printStackTrace();
					}
					p.move(input.charAt(i));		//input i번째 글짜 방향으로 플레이어 이동
					gView.pSetPos(p.id, p.curLoc);	//플레이어 현재위치로 다시 그리기
					
					if((p.curLoc.x<0||p.curLoc.y<0)||BGameModel.map[p.curLoc.x][p.curLoc.y].type==null || fbackward) {	//만약 -1번 행,열이나 맵에 갈수 없는 곳이거나 피버타임에 뒤로 가려하면
						BGameController.setInputStr(null);
						gView.showMsg("invalid_move");		//이동할 수 없는 곳 오류 메시지 출력
						p.curLoc.setLoc(sLoc.x, sLoc.y);	//sLoc으로 회귀
						gView.pSetPos(p.id, p.curLoc);		//플레이어 현재위치 다시 그리기
						validMove = false;					//invalid move
						if(cross_b)						//만약 다리를 건넌적이 있으면, 받은 다리카드 돌려줌
							p.deck.b_card--;
						break;							//input 나머지 안보고 break
					}else if(BGameModel.map[p.curLoc.x][p.curLoc.y].type.equals("E")) {		//End에 도착
						p.finish = true;	//플레이어 완주
						break;							//input 나머지 안보고 break
					}

				}
				//끝까지 이동
				if(input.length()==0) {		//만약 움직임수가 0이하이면, 0.5초 대기
					try {
						Thread.sleep(500);
					}catch(InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				if(cross_b) 							//다리를 건넜으면, 다리 카드 +1
					p.deck.b_card++;
				
			}
			//이동 완료
			BGameController.setInputStr(null);
			String curType = BGameModel.map[p.curLoc.x][p.curLoc.y].type; 
			if(curType.equals("P")) {			//현재 셀 종류가 P면 p카드 +1
				p.deck.p_card++;
			}else if(curType.equals("H")) {		//현재 셀 종류가 H면 h카드 +1
				p.deck.h_card++;
			}else if(curType.equals("S")) {		//현재 셀 종류가 S면 s카드 +1
				p.deck.s_card++;
			}
				
		}else {	//cmd error
			System.out.println("Command Error has occured!");
			System.exit(0);
		}
		//stay나 roll 행동 완료
		
		gView.ctrlUpdate("dice", 0, true); //컨트롤 패널 업데이트 (대상, 숫자, 초기화 여부) = (주사위,0, 초기화o)  
		gView.ctrlUpdate("move", 0, true);	//컨트롤 패널 업데이트 (대상, 숫자, 초기화 여부) = (움직임수,0, 초기화o)
		BGameController.cmdBtnEnable(true);	//stay, roll버튼 활성화
		BGameController.inputVisible(false);//input_field & submit 버튼 숨기기
		gView.statUpdate(p.id, p);			//플레이어 상태화면 최신화
		
		
	}
	public static void showResult() {		//결과 출력
		int[] score_result = new int[4];
		for(int i=0; i<4; i++) {
			if(i<BGameModel.playersNum) {
				score_result[i] = BGameModel.player[i].getPt();		//플레이어i 점수 계산 -> 점수결과[i]
			}else {
				score_result[i] = -1;
			}
		}
		gView.resultUpdate(score_result);	//점수결과 출력
	}
	public static void setScene(int n) {		//화면 번호 설정
		sceneNum = n;
	}
	public static int getScene() {				//화면 번호 가져오기
		return sceneNum;
	}
	public static void setFileName(String str) {	
		fName = str;
	}
	public static String getFileName() {	
		return fName;
	}
	public static void setCmd(String str) {	//커맨드 설정
		cmdStr = str;
	}
	public static String getCmd() {			//커맨드 가져오기
		return cmdStr;
	}
	public static void setInputStr(String str) {		//input 설정
		inputStr = str;
	}
	public static String getInputStr() {				//input 가져오기
		return inputStr;
	}
	public static void cmdBtnEnable(boolean flag) {	//stay roll 버튼 활성화 함수
		gView.cmdBtnEnable(flag);
	}
	public static void inputVisible(boolean flag) {	//input field & submit 버튼 보이기 활성
		gView.inputVisible(flag);
	}
	
}
