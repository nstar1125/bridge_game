/*
 * 수정일 :	22.06.03
 * 작성자 :	김상민
 */

class Location{		//위치
	int x;
	int y;
	Location(){
		x = 0;
		y = 0;
	}
	Location(int a, int b){
		x = a;
		y = b;
	}
	void setLoc(int a, int b){
		x = a;
		y = b;
	}
}


class Cell{			//셀
	String type;		//셀 종
	String pDir;		//다음 방향
	String nDir;		//이전 방향
	
	Cell(){
		type = null;
		pDir = null;
		nDir = null;
	}
}

class Player{		//플레이어
	int id;			//번호
	int rank;		//완주 순서
	Location curLoc;		//현재 플레이어 위치
	Deck deck;				//플레이어 카드 패
	boolean finish;			//완주 여부
	
	Player(int num){
		id = num;
		rank = 0;
		curLoc = new Location();
		deck = new Deck();
		finish = false;
	}
	void setLoc(int a, int b) {		//위치 설정
		curLoc.setLoc(a, b);
	}
	void move(char c) {			//상하좌우 이동
		if(c=='u' || c=='U') {
			curLoc.x--;
		}else if(c=='d' || c=='D') {
			curLoc.x++;
		}else if(c=='l' || c=='L') {
			curLoc.y--;
		}else if(c=='r' || c=='R') {
			curLoc.y++;
		}else {
			System.out.print("wrong command");
		}
	}
		
	int getPt() {		//총 점수 계산
		int totalPt=0;
		if(rank == 1) {
			totalPt+=7;
		}else if(rank == 2) {
			totalPt+=3;
		}else if(rank == 3) {
			totalPt+=1;
		}else {
			totalPt+=0;
		}
		totalPt = totalPt + deck.p_card*1 + deck.h_card*2 + deck.s_card*3;
		return totalPt;
	}
	
	
}

class Deck{			//카드 패
	int b_card;
	int p_card;
	int h_card;
	int s_card;
	Deck(){
		b_card = 0;
		p_card = 0;
		h_card = 0;
		s_card = 0;	
	}
}


public class BGameModel {
	static Cell[][] map;		//맵
	static int mapR, mapC;		//맵 행렬 길이
	static Location start;		//시작 위
	static Player[] player;		//플레이어
	static int playersNum;		//플레이어 

	BGameModel() {
		map = new Cell[50][50];
		mapR = 0;
		mapC = 0;
		start = new Location();
		player = null;
		playersNum = 0;
	}
	

}


