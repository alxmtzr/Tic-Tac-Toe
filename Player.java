public class Player {
    private boolean active = false;

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    private String sign;

    public boolean isActive(){
        return active;
    }
    public void setActive(boolean active){
        this.active = active;
    }
}
