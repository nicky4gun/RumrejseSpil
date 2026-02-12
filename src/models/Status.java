package models;

public class Status {
    private int fuel;
    private int integrity;
    private int scrapMetal;
    private int shield;
    private boolean repairKit;
    private String status;

    public Status(int fuel, int integrity ,int scrapMetal,int shield,boolean repairKit,String status  ){
        this.fuel = fuel;
        this.integrity = integrity;
        this.scrapMetal = scrapMetal;
        this.shield = shield;
        this.repairKit = repairKit;
        this.status = status;
    }

    public int getFuel() {
        return fuel;
    }

    public void setFuel(int fuel) {
        this.fuel = fuel;
    }

    public int getIntegrity() {
        return integrity;
    }

    public void setIntegrity(int integrity) {
        this.integrity = integrity;
    }

    public int getScrapMetal() {
        return scrapMetal;
    }

    public void setScrapMetal(int scrapMetal) {
        this.scrapMetal = scrapMetal;
    }

    public int getShield() {
        return shield;
    }

    public void setShield(int shield) {
        this.shield = shield;
    }

    public String getStatus(){
        return status;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public boolean getRepairKit(){
        return repairKit;
    }

    public void setRepairKit(boolean repairKit){
        this.repairKit = repairKit;
    }

    public String printStatus() {
        return "\n------------------------------\n" +
                "STATUS" + "\n" +
                "Fuel: " + fuel + "\n" +
                "Integrity: " + integrity + "\n" +
                "Scrap Metal: " + scrapMetal + "\n" +
                "Shield: " + shield + "\n" +
                "Repair Kit: " + repairKit + "\n" +
                "Status: " + status + "\n" +
                "------------------------------\n";
    }

}

