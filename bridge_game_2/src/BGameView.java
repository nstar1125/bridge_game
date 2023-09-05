/*
 * 수정일 :	22.06.04
 * 작성자 :	김상민
 */


import java.util.Scanner;



public class BGameView {
	private Scanner s;
	
	public BGameView(){
		s = new Scanner(System.in);
		
	}
	public void setFocus(String cmd) {				//화면 선택 함수
		switch(cmd) {
		case "title":						//제목화면
			System.out.println("The Bridge Game");
			System.out.println("");
			System.out.print("press enter to start... ");
			s.nextLine();
			System.out.println("");
	        BGameController.setScene(1);
			break;
		case "fileIn":						//파일입력화면
			System.out.print("맵 파일을 입력하세요(ex. default.map)... ");
			BGameController.setFileName("map/"+s.nextLine());
			BGameController.setScene(2);
			break;
		case "lobby":						//로비화면
			System.out.print("플레이어 수를 입력하세요(2~4)... ");
			BGameModel.playersNum = Integer.parseInt(s.nextLine());
			System.out.println("");
			BGameController.setScene(3);
			break;
		case "stage":						//게임화면
			System.out.println("");
			break;
		case "result":						//결과화면
			System.out.println("********** 게임 결과 **********");
			break;
		}
	}
	public void addMap() {						//맵 그리기
		for(int i = 0; i<BGameModel.mapR; i++) {
			for(int j = 0; j<BGameModel.mapC; j++) {
				if(BGameModel.map[i][j].type!=null) {
					if(BGameModel.map[i][j].type.equals("C")) {
						System.out.print("   C");
					}else if(BGameModel.map[i][j].type.equals("P")) {
						System.out.print("   P");
					}else if(BGameModel.map[i][j].type.equals("H")) {
						System.out.print("   H");
					}else if(BGameModel.map[i][j].type.equals("S")) {
						System.out.print("   S");
					}else if(BGameModel.map[i][j].type.equals("B")) {
						System.out.print("   B");
					}else if(BGameModel.map[i][j].type.equals("J")) {
						System.out.print("   J");
					}else if(BGameModel.map[i][j].type.equals("b")) {
						System.out.print("   b");
					}else if(BGameModel.map[i][j].type.equals("St")) {
						System.out.print("   S");
					}else if(BGameModel.map[i][j].type.equals("E")) {
						System.out.print("   E");
					}else {
						System.out.print("   ?");
					}
					System.out.print("\t");
				}else {
					System.out.print("   ");
					System.out.print("\t");
				}
			}
			System.out.print("|\t");
			System.out.println(i);
			System.out.println();
			if(i==BGameModel.mapR-1) {
				for(int j = 0; j<BGameModel.mapC; j++) {
					System.out.print("-------\t");
				}
				System.out.println();
				for(int j = 0; j<BGameModel.mapC; j++) {
					System.out.print("   "+j);
					System.out.print("\t");
				}
				System.out.println();
			}
		}
	}
	public void pSetPos(int i, int n, int m) {			//플레이어 위치 표시
		System.out.println("Player"+(i+1)+" 위치 : "+"( "+m+" , "+n+" )");
	}
	public void pSetPos(int i, Location l) {			//플레이어 위치 표시
		System.out.println("Player"+(i+1)+" 위치 : "+"( "+l.y+" , "+l.x+" )");
	}
	public void addPlayers(int pNum) {}
	public void setFever(boolean flag) {					//피버타임
		if(flag) {
			System.out.println("@@@@@@@@ 현재 피버타임입니다! @@@@@@@@");
			System.out.println("");
		}else {
			
		}
	}
	public void statTurn(int i, boolean flag, boolean finish) {
		if(finish) {												//플레이어i 완주
			System.out.println("Player "+(i+1)+"가 완주했습니다.");
		}else {
			if(flag) {
				for(int a = 0; a<BGameModel.mapR; a++) {			//맵 위의 플레이어 표시
					for(int b = 0; b<BGameModel.mapC; b++) {
						if(BGameModel.player[i].curLoc.x==a && BGameModel.player[i].curLoc.y==b) {
							System.out.print("  *P"+(i+1)+"\t");
						}else {
							if(BGameModel.map[a][b].type!=null) {
								if(BGameModel.map[a][b].type.equals("C")) {
									System.out.print("   C");
								}else if(BGameModel.map[a][b].type.equals("P")) {
									System.out.print("   P");
								}else if(BGameModel.map[a][b].type.equals("H")) {
									System.out.print("   H");
								}else if(BGameModel.map[a][b].type.equals("S")) {
									System.out.print("   S");
								}else if(BGameModel.map[a][b].type.equals("B")) {
									System.out.print("   B");
								}else if(BGameModel.map[a][b].type.equals("J")) {
									System.out.print("   J");
								}else if(BGameModel.map[a][b].type.equals("b")) {
									System.out.print("   b");
								}else if(BGameModel.map[a][b].type.equals("St")) {
									System.out.print("   S");
								}else if(BGameModel.map[a][b].type.equals("E")) {
									System.out.print("   E");
								}else {
									System.out.print("   ?");
								}
								System.out.print("\t");
							}else {
								System.out.print("   ");
								System.out.print("\t");
							}
						}
						
						
						
					}
					System.out.print("|\t");
					System.out.println(a);
					System.out.println();
					System.out.println();
					if(a==BGameModel.mapR-1) {
						for(int b = 0; b<BGameModel.mapC; b++) {
							System.out.print("-------\t");
						}
						System.out.println();
						for(int b = 0; b<BGameModel.mapC; b++) {
							System.out.print("   "+b);
							System.out.print("\t");
						}
						System.out.println();
					}
				}
				
				
				System.out.println("********** Player "+(i+1)+"의 턴 **********");
				System.out.print("stay나 roll을 입력하세요... ");		//커맨드 입력
				BGameController.setCmd(s.nextLine());
			}else {
				
			}
		}
	}
	public void ctrlUpdate(String option, int n, boolean init) {	//컨트롤 패널 업데이트 (대상, 숫자, 초기화 여부)
		switch(option) {
		case "dice":
			if(init) {

			}else {
				System.out.println("주사위 값 : "+n);
			}
			break;
		case "move":
			if(init) {
				
			}else {
				System.out.println("가능한 움직임 수 : "+n);
				if(n>0) {
					System.out.print("움직임을 입력하세요(ex. RRDDD)... ");
					BGameController.setInputStr(s.nextLine());				//input 입력
				}
			}
			break;
		default:
			break;	
		}
	}
	public void showMsg(String cmd) {				//메시지 출력
		switch(cmd) {
		case "wrong_input":
			System.out.println("잘못된 입력입니다.");
			System.out.print("움직임을 입력하세요(ex. RRDDD)... ");
			BGameController.setInputStr(s.nextLine());
			break;
		case "wrong_length":
			System.out.println("입력된 값이 가능한 움직임 수와 다릅니다.");
			System.out.print("움직임을 입력하세요(ex. RRDDD)... ");
			BGameController.setInputStr(s.nextLine());
			break;
		case "invalid_move":
			System.out.println("갈 수 없는 곳입니다.");
			System.out.print("움직임을 입력하세요(ex. RRDDD)... ");
			BGameController.setInputStr(s.nextLine());
			break;
		}
		
	}
	public void statUpdate(int i, Player p) {			//플레이어 상태 출력
		System.out.println("---------- Player "+(i+1)+" 카드 목록 ----------");
		System.out.println("- B card : "+p.deck.b_card);
		System.out.println("- P card : "+p.deck.p_card);
		System.out.println("- H card : "+p.deck.h_card);
		System.out.println("- S card : "+p.deck.s_card);
		System.out.println("");
		System.out.println("");
		System.out.println("");
	}
	public void resultUpdate(int[] ary) {				//게임 결과 출력
		for(int i = 0; i<ary.length; i++) {
			if(ary[i]!=-1) {
				System.out.println("Player "+(i+1)+" 점수 : "+ary[i]+"점");
			}
		}
		System.out.println("press enter to terminate... ");
		s.nextLine();
		BGameController.setScene(-1);
	}
	public void buildUI() {};	//컨트롤러 호환용 의미 없는 함수들
	public void cmdBtnEnable(boolean b) {};
	public void inputVisible(boolean b) {};
	public void setSubmitBtnEnabled (boolean b) {};
	public void setTextfieldText(String a, String b) {};
	public String getTextfieldText(String a) {return null;};
}

