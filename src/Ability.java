public abstract class Ability {
    private boolean isUsed;
    public InGameData _data = GameManager.getInstance().get_gameData();
    public InGameView _view = GameManager.getInstance().get_inGame();
    public String abilityName;

    public void run(Player user)
    {
        if(user != _data.activatedPlayer) return;
        if(!isUsed) {
            use();
            //isUsed = true;
        }
    }
    public Ability(){
        isUsed = false;
    }//constructor


    public abstract void use();
    public boolean isUsed(){ return isUsed; }
    public void setUsed(boolean b){isUsed = b;}


}

