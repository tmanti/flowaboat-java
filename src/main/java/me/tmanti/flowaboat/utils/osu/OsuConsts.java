package me.tmanti.flowaboat.utils.osu;

public enum OsuConsts {
    AR_MS_STEP1  (120),
    AR_MS_STEP2 (150),
    AR0_MS (1800),
    AR5_MS (1200),
    AR10_MS (450),
    OD_MS_STEP (6),
    OD0_MS (79.5F),
    OD10_MS (19.5F),

    DT_SPD (1.5F),
    HT_SPD (.75F),

    HR_AR (1.4F),
    EZ_AR (0.5F),

    HR_CS (1.3F),
    EZ_CS (0.5F),

    HR_OD (1.4F),
    EZ_OD (0.5F),

    HR_HP (1.4F),
    EZ_HP (0.5F),

    STRAIN_STEP (400.0F),
    SPEED_DECAY_BASE (0.3F),
    AIM_DECAY_BASE (0.15F),
    STAR_SCALING_FACTOR (0.0675F),
    EXTREME_SCALING_FACTOR (0.5F),
    DECAY_WEIGHT (0.9F);

    float value;
    OsuConsts(int value){
        this.value = (float) value;
    }
    OsuConsts(float value) {
        this.value = value;
    }

}
