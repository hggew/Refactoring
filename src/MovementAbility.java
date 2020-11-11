public class MovementAbility {
//변수값(throwResult)을 지정하는 부분 set 메서드를 사용하도록 변경
    //모 아니면 도
    public static class MoOrDo extends Ability{
        int randomNum;
        @Override
        public void use() {
            _data.activatedPlayer.isNowAbility1Use=true;
            randomNum= (int)(Math.random()*2);
            System.out.println("MD randomNum >>" + randomNum);
            if(randomNum==0){ _data.throwResult = 5;   }            //모
            else{ _data.throwResult = 1;   }                        //도
        }
    }

    //개 아니면 걸
    public static class GaeOrGirl extends Ability{
        int randomNum;
        @Override
        public void use() {
            _data.activatedPlayer.isNowAbility1Use=true;
            randomNum= (int)(Math.random()*2);
            System.out.println("GG randomNum >>" + randomNum);
            if(randomNum==0){   _data.throwResult = 2;   }            //개
            else{   _data.throwResult = 3;   }                        //걸
        }
    }

    //던지면 무조건 윷
    public static class OnlyYut extends Ability{
        @Override
        public void use(){
            _data.activatedPlayer.isNowAbility1Use=true;
            System.out.println("only yut");
            _data.throwResult=4;
        }
    }

}
