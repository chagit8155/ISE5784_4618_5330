package scene;


import geometries.Geometries;
import lighting.AmbientLight;
import lighting.LightSource;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;

/**
 * Scene class represents a 3D scene.
 * It holds the name, background color, ambient light, and geometries of the scene.
 */
public class Scene {

    /**
     * The name of the scene
     */
    public String name;

    /**
     * The background color of the scene, initialized to black
     */
    public Color background = Color.BLACK;

    /**
     * The ambient light of the scene, initialized to NONE
     */
    public AmbientLight ambientLight = AmbientLight.NONE;

    /**
     * The geometries in the scene, initialized to an empty Geometries object
     */
    public Geometries geometries = new Geometries();

    /**
     * List of light sources in the scene.
     */
    public List<LightSource> lights = new LinkedList<>();




    /**
     * Constructor that accepts the name of the scene
     *
     * @param name the name of the scene
     */
    public Scene(String name) {
        this.name = name;
    }



//  ********** Setter *******************
    /**
     * Sets the background color of the scene
     *
     * @param background the background color
     * @return the scene itself
     */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    /**
     * Sets the ambient light of the scene
     *
     * @param ambientLight the ambient light
     * @return the scene itself
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    /**
     * Sets the geometries of the scene
     *
     * @param geometries the geometries
     * @return the scene itself
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }
    /**
     * Adds a light source to the scene.
     *
     * @param light The light source to add.
     * @return The current scene instance for method chaining.
     */
    public Scene addLight(LightSource light) {
        lights.add(light);
        return this;
    }



    /**
     * Sets the lights of the scene
     *
     * @param lights the geometries
     * @return the scene itself
     */
    public Scene setLights(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }
}
