import javafx.scene.Parent;
import org.apache.coyote.http11.filters.VoidOutputFilter;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * @author 98248
 * @Date: 2022/9/25 - 09 - 25 - 18:26
 * @Description: PACKAGE_NAME
 * @version: 1.0
 */

public class test {
    public static void main(String[] args) {
        // File file = new File("file");
        // file.mkdir();
        String[] str = new String[]{".jpg",".txt",".png"};
        Random random = new Random();
        for(int i=1;i<10;i++){
            File file1 = new File("file/file" + random.nextInt()+str[random.nextInt(str.length)]);
            try {
                boolean newFile = file1.createNewFile();
                System.out.println("创建状况"+newFile+"文件路径"+file1.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @Test
    public void test() throws IOException {
        File file = new File("file/text");
        if(!file.exists()){
            boolean newFile = file.mkdir();
            System.out.println(newFile);

        }
        System.out.println(file.getAbsolutePath());
    }
    @Test
    public void deleteFile(){
        File file = new File("file");
        File[] files = file.listFiles();
        listDirectory(files);
    }
    public void listDirectory(File[] files){
        for (File file : files) {
            boolean flag = file.isDirectory();
            String str = "    ";
            while(flag){
                System.out.println("目录："+file.getName()+"下");
                int Filecount =0;//当前目录下子文件数量
                int Dircetory = 0;//当前目录下子目录数量
                for(File fil: file.listFiles()){
                    if(fil.isFile()){
                        Filecount++;
                        System.out.println(str+"子文件："+fil.getName()+" 路径为"+fil.getAbsolutePath());
                    }else{
                        Dircetory++;
                        System.out.println(str+"子目录："+fil.getName()+" 路径为"+fil.getAbsolutePath());
                        File[] files1 = fil.listFiles();
                        str = str+str;
                        listDirectory(files1);
                    }
                }
                flag=false;
                str = "    ";
                System.out.println("目录："+file.getName()+"下"+"子目录："+Dircetory+"个 子文件"+Filecount+"个");
            }
            if(file.isFile()) System.out.println(str+"子文件："+file.getName()+" 路径为"+file.getAbsolutePath());
        }
    }
}
