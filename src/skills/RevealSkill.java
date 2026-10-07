package skills;
/*Clase para la habilidad que revela posiciones de tesoros. Provisional aun hay que modificarla */
public class RevealSkill extends Skill {
    public static final double MAX_RATIO = 0.25;
    private final double ratio;
    public RevealSkill(String name, String desc, double ratio) {
        super(name, desc, ESkillType.TREASURE);
        this.ratio = Math.min(ratio, MAX_RATIO);
    }
    @Override public double getRevealedRatio() { return ratio; }
}
