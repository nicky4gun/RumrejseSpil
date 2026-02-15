package models;

public class Status {
    private int fuel;
    private int integrity;
    private int scrapMetal;
    private int shield;
    private boolean repairKitUsed;

    public Status(int fuel, int integrity, int scrapMetal, int shield, boolean repairKitUsed) {
        this.fuel = fuel;
        this.integrity = integrity;
        this.scrapMetal = scrapMetal;
        this.shield = shield;
        this.repairKitUsed = repairKitUsed;
    }

    public int getFuel() {
        return fuel;
    }

    public void setFuel(int fuel) {
        this.fuel = Math.max(0, Math.min(100, fuel));
    }

    public int getIntegrity() {
        return integrity;
    }

    public void setIntegrity(int integrity) {
        this.integrity = Math.max(0, Math.min(100, integrity));
    }

    public int getScrapMetal() {
        return scrapMetal;
    }

    public void setScrapMetal(int scrapMetal) {
        this.scrapMetal = Math.max(0, scrapMetal);
    }

    public int getShield() {
        return shield;
    }

    public void setShield(int shield) {
        this.shield = Math.max(0, shield);
    }

    public boolean getRepairKitUsed(){
        return repairKitUsed;
    }

    public void setRepairKitUsed(boolean repairKitUsed){
        this.repairKitUsed = repairKitUsed;
    }

    public String getRepairKitStatus() {
        if (repairKitUsed) {
            return "Used";
        } else {
            return "Available";
        }
    }

    public String printStatus() {
        return "\n------------------------------\n" +
                "STATUS" + "\n" +
                "Fuel: " + fuel + "\n" +
                "Integrity: " + integrity + "\n" +
                "Scrap Metal: " + scrapMetal + "\n" +
                "Shield: " + shield + "\n" +
                "Repair Kit: " + getRepairKitStatus() + "\n" +
                "------------------------------\n";
    }
}

