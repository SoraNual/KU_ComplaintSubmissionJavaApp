package ku.cs.form.services;

import javafx.scene.image.ImageView;

import java.io.*;

public abstract class UploadPicture {

    protected void addPic(String username, ImageView profile_pic) { //for user
        String from = profile_pic.getImage().getUrl();
        String target = "data" + File.separator + "img" + File.separator + username + ".jpg";

        FileInputStream in = null;
        BufferedInputStream bin = null;
        FileOutputStream out = null;
        BufferedOutputStream bout = null;

        try {
            in = new FileInputStream(from);
            bin = new BufferedInputStream(in);
            out = new FileOutputStream(target);
            bout = new BufferedOutputStream(out);
            byte[] line = bin.readAllBytes();
            while(line.length != 0){
                bout.write(line);
                line = bin.readAllBytes();
            }
            out.flush();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                bout.close();
                out.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }




}
