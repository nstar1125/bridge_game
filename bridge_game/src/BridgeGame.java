/*
 * 수정일 :	22.06.03
 * 작성자 :	김상민
 */

public class BridgeGame {

	public static void main(String[] args) {
		new BGameModel();						//모델 생성
		BGameView gView = new BGameView();		//뷰 생성
		new BGameController(gView);				//컨트롤러 생성

	}

}
