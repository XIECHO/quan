package util;

import javafx.scene.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.Sphere;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class DrawBloch {
     Group all = new Group();
     final XformWorld world = new XformWorld();
     final PerspectiveCamera camera = new PerspectiveCamera(true);
     final XformCamera cameraXform = new XformCamera();

    private  final double CAMERA_INITIAL_DISTANCE = -1000;
    private  final double CAMERA_NEAR_CLIP = 0.1;
    private  final double CAMERA_FAR_CLIP = 10000.0;

     double mousePosX, mousePosY, mouseOldX, mouseOldY, mouseDeltaX, mouseDeltaY;


    public  Scene drawBloch(double[] a){

//        for(double b :a){
//            System.out.println(b);
//        }
        all.getChildren().add(world);
        all.setDepthTest(DepthTest.ENABLE);
        buildCamera();
        buildSphere(a);
        buildAxes();

        Scene scene = new Scene(all,400,300,true);
        scene.setFill(Color.ALICEBLUE);

        handleMouse(scene);
        scene.setCamera(camera);
        return scene;

    }



    public  void buildSphere(double[] a){
        Sphere sphere = new Sphere(80);
        sphere.setDrawMode(DrawMode.LINE);
        all.getChildren().add( sphere);


        PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setDiffuseColor(Color.DARKRED);
        redMaterial.setSpecularColor(Color.RED);
        Sphere sphere1 = new Sphere(5.5);
        sphere1.setMaterial(redMaterial);

        //偏移
        for(double b :a ){
            System.out.println(b);
        }

        sphere1.setTranslateZ(-80*a[0]);
        sphere1.setTranslateX(80*a[1]);
        sphere1.setTranslateY(-80*a[2]);
        all.getChildren().add(sphere1);
    }


    //画三根轴
    public   void buildAxes(){
        buildAxis("x");
        buildAxis("y");
        buildAxis("z");
    }
    //画一根轴
    public  void buildAxis(String key ){
        Cylinder axis= new Cylinder(1,240);


        Text text = new Text(key);
        switch (key){
            case "x":
                axis.getTransforms().add(new Rotate(90,  Rotate.X_AXIS));
                text.setTranslateX(130);
                text.setText("y");
                break;
            case "y":
                axis.getTransforms().add(new Rotate(0,  Rotate.Y_AXIS));
                text.setTranslateY(-130);
                text.setText("z");
                break;
            case "z":
                axis.getTransforms().add(new Rotate(90,  Rotate.Z_AXIS));
                text.setTranslateZ(-130);
                text.setText("x");
                break;
        }

        all.getChildren().add(axis);
        all.getChildren().add(text);
    }


    private  void buildCamera() {
        all.getChildren().add(cameraXform);
        cameraXform.getChildren().add(camera);
        camera.setNearClip(CAMERA_NEAR_CLIP);
        camera.setFarClip(CAMERA_FAR_CLIP);
        camera.setTranslateZ(CAMERA_INITIAL_DISTANCE);
    }

    private  void handleMouse(Scene scene) {
        scene.setOnMousePressed((MouseEvent me) -> {
            mousePosX = me.getSceneX();
            mousePosY = me.getSceneY();
            mouseOldX = me.getSceneX();
            mouseOldY = me.getSceneY();
        });
        scene.setOnMouseDragged((MouseEvent me) -> {
            mouseOldX = mousePosX;
            mouseOldY = mousePosY;
            mousePosX = me.getSceneX();
            mousePosY = me.getSceneY();
            mouseDeltaX = (mousePosX - mouseOldX);
            mouseDeltaY = (mousePosY - mouseOldY);
            if (me.isPrimaryButtonDown()) {
                cameraXform.ry.setAngle(cameraXform.ry.getAngle() + mouseDeltaX * 0.2);
                cameraXform.rx.setAngle(cameraXform.rx.getAngle() - mouseDeltaY * 0.2);
            }
        });
    }

}

class XformWorld extends Group {

    final Translate t = new Translate(0.0, 0.0, 0.0);
    final Rotate rx = new Rotate(0, 0, 0, 0, Rotate.X_AXIS);
    final Rotate ry = new Rotate(0, 0, 0, 0, Rotate.Y_AXIS);
    final Rotate rz = new Rotate(0, 0, 0, 0, Rotate.Z_AXIS);

    public XformWorld() {
        super();
        this.getTransforms().addAll(t, rx, ry, rz);

    }

}




class XformCamera extends Group {

    final Translate t = new Translate(0.0, 0.0, 0.0);
    final Rotate rx = new Rotate(0, 0, 0, 0, Rotate.X_AXIS);
    final Rotate ry = new Rotate(0, 0, 0, 0, Rotate.Y_AXIS);
    final Rotate rz = new Rotate(0, 0, 0, 0, Rotate.Z_AXIS);

    public XformCamera() {
        super();
        this.getTransforms().addAll(t, rx, ry, rz);
    }
}
