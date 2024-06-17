package primitives;

/**
 * Material class represents the material properties of a geometric object.
 * This class follows the Phong reflection model to represent how light interacts with the material.
 */
public class Material {

    /**
     * The diffuse coefficient of the material.
     */
    public Double3 kd = Double3.ZERO;

    /**
     * The specular coefficient of the material.
     */
    public Double3 ks = Double3.ZERO;

    /**
     * The reflection coefficient of the material.
     */
    public Double3 kr = Double3.ZERO;

    /**
     * The transparency coefficient of the material.
     */
    public Double3 kt = Double3.ZERO;

    /**
     * The shininess of the material, which affects the specular highlight.
     */
    public int shininess = 1;

    /**
     * Sets the diffuse coefficient (kd) of the material.
     *
     * @param kd the diffuse coefficient to set
     * @return the current Material object for chaining
     */
    public Material setKd(Double3 kd) {
        this.kd = kd;
        return this;
    }

    /**
     * Sets the diffuse coefficient (kd) of the material.
     *
     * @param kd the diffuse coefficient to set
     * @return the current Material object for chaining
     */
    public Material setKd(double kd) {
        this.kd = new Double3(kd);
        return this;
    }

    /**
     * Sets the specular coefficient (ks) of the material.
     *
     * @param ks the specular coefficient to set
     * @return the current Material object for chaining
     */
    public Material setKs(Double3 ks) {
        this.ks = ks;
        return this;
    }

    /**
     * Sets the specular coefficient (ks) of the material.
     *
     * @param ks the specular coefficient to set
     * @return the current Material object for chaining
     */
    public Material setKs(double ks) {
        this.ks = new Double3(ks);
        return this;
    }

    /**
     * Sets the reflection coefficient (kr) of the material.
     *
     * @param kr the reflection coefficient to set
     * @return the current Material object for chaining
     */
    public Material setKr(Double3 kr) {
        this.kr = kr;
        return this;
    }

    /**
     * Sets the reflection coefficient (kr) of the material.
     *
     * @param kr the reflection coefficient to set
     * @return the current Material object for chaining
     */
    public Material setKr(double kr) {
        this.kr = new Double3(kr);
        return this;
    }

    /**
     * Sets the transparency coefficient (kt) of the material.
     *
     * @param kt the transparency coefficient to set
     * @return the current Material object for chaining
     */
    public Material setKt(Double3 kt) {
        this.kt = kt;
        return this;
    }

    /**
     * Sets the transparency coefficient (kt) of the material.
     *
     * @param kt the transparency coefficient to set
     * @return the current Material object for chaining
     */
    public Material setKt(double kt) {
        this.kt = new Double3(kt);
        return this;
    }

    /**
     * Sets the shininess of the material.
     *
     * @param shininess the shininess to set
     * @return the current Material object for chaining
     */
    public Material setShininess(int shininess) {
        this.shininess = shininess;
        return this;
    }
}
