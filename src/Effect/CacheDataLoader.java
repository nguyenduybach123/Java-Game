/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Effect;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Hashtable;
import javax.imageio.ImageIO;

/**
 *
 * @author Admin
 */
public class CacheDataLoader {
    
    private static CacheDataLoader instance = null;
    
    //private String framefile = "data/frame.txt";
    //private String animationfile = "data/animation.txt";
    private String framefile = "data/fra.txt";
    private String animationfile = "data/ani.txt";
    private String physmapfile = "data/phys_map_1.txt";
    private String backgroundmapfile = "data/background_map_1.txt";
    private String soundfile = "data/sounds.txt";
    
    private Hashtable<String, FrameImage> frameImages; 
    private Hashtable<String, Animation> animations;
    private Hashtable<String, AudioClip> sounds;
    
    private int[][] phys_map;
    private int[][] background_map;
    
    private CacheDataLoader() {}

    public static CacheDataLoader getInstance(){
        if(instance == null)
            instance  = new CacheDataLoader();
        return instance;
    }
    
    public AudioClip getSound(String name){
        return instance.sounds.get(name);
    }
    
    public Animation getAnimation(String name){
        
        Animation animation = new Animation(instance.animations.get(name));
        return animation;
        
    }
    
    public FrameImage getFrameImage(String name){

        FrameImage frameImage = new FrameImage(instance.frameImages.get(name));
        return frameImage;

    }
    
    public int[][] getPhysicalMap(){
        return instance.phys_map;
    }
    
    public int[][] getBackgroundMap(){
        return instance.background_map;
    }
    
    public void LoadData()throws IOException{
        
        LoadFrame();
        System.out.println("done frame");
        LoadAnimation();
        System.out.println("done animation");
        LoadPhysMap();
        System.out.println("done phys map");
        LoadBackgroundMap();
        System.out.println("done background map");
        LoadSounds();
        System.out.println("done music");
        
    }
    
    public void LoadSounds() throws IOException{
        sounds = new Hashtable<String, AudioClip>();
        
        FileReader fr = new FileReader(soundfile);
        BufferedReader br = new BufferedReader(fr);
        
        String line = null;
        
        if(br.readLine()==null) { // no line = "" or something like that
            System.out.println("No data");
            throw new IOException();
        }
        else {
            
            fr = new FileReader(soundfile);
            br = new BufferedReader(fr);
            
            while((line = br.readLine()).equals(""));
            
            int n = Integer.parseInt(line);
            
            for(int i = 0;i < n; i ++){
                
                AudioClip audioClip = null;
                while((line = br.readLine()).equals(""));

                String[] str = line.split(" ");
                String name = str[0];
                
                String path = str[1];

                try {
                   audioClip =  Applet.newAudioClip(new URL("file","",str[1]));

                } catch (MalformedURLException ex) {}
                
                instance.sounds.put(name, audioClip);
            }
            
        }
        
        br.close();
        
    }
    
    public void LoadBackgroundMap() throws IOException{
        
        FileReader fr = new FileReader(backgroundmapfile);
        BufferedReader br = new BufferedReader(fr);
        
        String line = null;
        
        line = br.readLine();
        int numberOfRows = Integer.parseInt(line);
        line = br.readLine();
        int numberOfColumns = Integer.parseInt(line);
            
        
        instance.background_map = new int[numberOfRows][numberOfColumns];
        
        for(int i = 0;i < numberOfRows;i++){
            line = br.readLine();
            String [] str = line.split(" |  ");
            for(int j = 0;j<numberOfColumns;j++)
                instance.background_map[i][j] = Integer.parseInt(str[j]);
        }
        
        for(int i = 0;i < numberOfRows;i++){
            
            for(int j = 0;j<numberOfColumns;j++)
                System.out.print(" "+instance.background_map[i][j]);
            
            System.out.println();
        }
        
        br.close();
        
    }
    
    public void LoadPhysMap() throws IOException{
        
        FileReader fr = new FileReader(physmapfile);
        BufferedReader br = new BufferedReader(fr);
        
        String line = null;
        
        line = br.readLine();
        int numberOfRows = Integer.parseInt(line);
        line = br.readLine();
        int numberOfColumns = Integer.parseInt(line);
            
        
        instance.phys_map = new int[numberOfRows][numberOfColumns];
        
        for(int i = 0;i < numberOfRows;i++){
            line = br.readLine();
            String [] str = line.split(" ");
            System.out.println("row : " + i);
            for(int j = 0;j<numberOfColumns;j++)
                instance.phys_map[i][j] = Integer.parseInt(str[j]);
        }
        
        for(int i = 0;i < numberOfRows;i++){
            
            for(int j = 0;j<numberOfColumns;j++)
                System.out.print(" "+instance.phys_map[i][j]);
            
            System.out.println();
        }
        
        br.close();
        
    }
    

    
    public void LoadAnimation() throws IOException {
        
        animations = new Hashtable<String, Animation>();
        
        FileReader fr = new FileReader(animationfile);
        BufferedReader br = new BufferedReader(fr);
        
        String line = null;
        
        if(br.readLine()==null) {
            System.out.println("No data");
            throw new IOException();
        }
        else {
            
            fr = new FileReader(animationfile);
            br = new BufferedReader(fr);
            
            while((line = br.readLine()).equals(""));
            int n = Integer.parseInt(line);
            
            for(int i = 0;i < n; i++){
                
                Animation animation = new Animation();
                
                while((line = br.readLine()).equals(""));
                animation.setName(line);

                while((line = br.readLine()).equals(""));
                String[] str = line.split(" ");
                
                for(int j = 0;j<str.length;j+=2){
                    animation.add(getFrameImage(str[j]), Double.parseDouble(str[j+1]));
                }

                instance.animations.put(animation.getName(), animation);

            }
            
        }
        
        br.close();
    }
    
    
    public void LoadFrame() throws IOException{
        
        frameImages = new Hashtable<String, FrameImage>();
        
        FileReader fr = new FileReader(framefile);
        BufferedReader br = new BufferedReader(fr);
        
        String line = null;
        
        if(br.readLine()==null) {
            System.out.println("No data");
            throw new IOException();
        }
        else {
            
            fr = new FileReader(framefile);
            br = new BufferedReader(fr);
            
            while((line = br.readLine()).equals(""));
            
            int n = Integer.parseInt(line);
            String path = null;
            BufferedImage imageData = null;
            
            
            for(int i = 0;i < n; i++){
                
                FrameImage frame = new FrameImage();
                while((line = br.readLine()).equals(""));
                frame.setName(line);
                
                while((line = br.readLine()).equals(""));
                String[] str = line.split(" ");
                
                boolean refreshImage = (path == null || !path.equals(str[1]));
                path = str[1];

                
                while((line = br.readLine()).equals(""));
                str = line.split(" ");
                int x = Integer.parseInt(str[1]);
                
                while((line = br.readLine()).equals(""));
                str = line.split(" ");
                int y = Integer.parseInt(str[1]);
                
                while((line = br.readLine()).equals(""));
                str = line.split(" ");
                int w = Integer.parseInt(str[1]);
                
                while((line = br.readLine()).equals(""));
                str = line.split(" ");
                int h = Integer.parseInt(str[1]);
                
                if(refreshImage) {
                    refreshImage = false;
                    imageData = ImageIO.read(new File(path));
                }
                if(imageData != null) {
                    BufferedImage image = imageData.getSubimage(x, y, w, h);
                    frame.setImage(image);
                }

                instance.frameImages.put(frame.getName(), frame);
                
            }
            
        }
        
        br.close();
        
    }
    
}
