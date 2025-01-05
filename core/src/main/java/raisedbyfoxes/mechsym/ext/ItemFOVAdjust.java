package raisedbyfoxes.mechsym.ext;

public interface ItemFOVAdjust {
    default float getFOVMultiplier(int useTicks) {
        return 1F;
    }
}
