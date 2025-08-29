package UFCFighterManagement.dto;

public class FighterDTO {
    private int id;
    private String name;
    private int age;
    private String weightClass;
    private String record;
    private String stance;
    private double reach;
    private String coach;
    private String promotion;

    // Constructors
    public FighterDTO() {
        this.promotion = "UFC"; // Default to UFC
    }

    public FighterDTO(int id, String name, int age, String weightClass, String record, String stance, double reach, String coach, String promotion) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.weightClass = weightClass;
        this.record = record;
        this.stance = stance;
        this.reach = reach;
        this.coach = coach;
        this.promotion = promotion != null ? promotion : "UFC";
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getWeightClass() { return weightClass; }
    public void setWeightClass(String weightClass) { this.weightClass = weightClass; }

    public String getRecord() { return record; }
    public void setRecord(String record) { this.record = record; }

    public String getStance() { return stance; }
    public void setStance(String stance) { this.stance = stance; }

    public double getReach() { return reach; }
    public void setReach(double reach) { this.reach = reach; }

    public String getCoach() { return coach; }
    public void setCoach(String coach) { this.coach = coach; }

    public String getPromotion() { return promotion; }
    public void setPromotion(String promotion) { this.promotion = promotion; }

    @Override
    public String toString() {
        return "FighterDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", weightClass='" + weightClass + '\'' +
                ", record='" + record + '\'' +
                ", stance='" + stance + '\'' +
                ", reach=" + reach +
                ", coach='" + coach + '\'' +
                ", promotion='" + promotion + '\'' +
                '}';
    }

    public String toJson() {
        return String.format(
                "{\"id\":%d,\"name\":\"%s\",\"age\":%d,\"weightClass\":\"%s\",\"record\":\"%s\",\"stance\":\"%s\",\"reach\":%.1f,\"coach\":\"%s\",\"promotion\":\"%s\"}",
                this.id,
                this.name != null ? this.name.replace("\"", "\\\"") : "",
                this.age,
                this.weightClass != null ? this.weightClass.replace("\"", "\\\"") : "",
                this.record != null ? this.record.replace("\"", "\\\"") : "",
                this.stance != null ? this.stance.replace("\"", "\\\"") : "",
                this.reach,
                this.coach != null ? this.coach.replace("\"", "\\\"") : "",
                this.promotion != null ? this.promotion.replace("\"", "\\\"") : ""
        );
    }
}

