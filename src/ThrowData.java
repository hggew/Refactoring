public class ThrowData {
    //필드
    public int result;      //InGameController에서 던진 윷의 결과값 저장하기 위한 변수
    public Pawn preview;    //말을 옮길 수 있는 위치 미리보기를 위한 pawn

    //생성자
    public ThrowData(int r){
        result = r;
        preview = new Pawn();
        preview.setEnabled(false);      //비활성화
        preview.setVisible(false);      //화면에 보이지 않음
    }
}
