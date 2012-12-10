package com.credit.util;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
/**
 *@author Hanlu
 * 
 */
public class ZipUtils {
    /***************************************************************************
     * @param zipPath 压缩后文件路径
     * @param zipName 压缩后文件名
     * @param sourcePath 所要压缩的文件路径，可以是目录
     * @param isZipDir 是否包括目录
     * @throws IOException
     **************************************************************************/
    public static void getZip(String zipPath ,String zipName,String sourcePath, boolean isZipDir) throws IOException{
    	File floder=new File(zipPath);
    	if(!floder.exists())
    	{
    		floder.mkdirs();
    	}
    	OutputStream os = new FileOutputStream(zipPath+zipName);
        BufferedOutputStream bs = new BufferedOutputStream(os);
        ZipOutputStream zo = new ZipOutputStream(bs);
        zo.setEncoding("gbk");
        zip(sourcePath,new File(sourcePath), zo, isZipDir);
        zo.closeEntry();
        zo.close();
    }
    /*************************************************************************
     * @param path 要压缩的路径, 可以是目录, 也可以是文件.
     * @param basePath 如果path是目录,它一般为new File(path), 作用是:使输出的zip文件以此目录为根目录
     * @param zo 压缩输出流
     * @param isZipDir 是否包括目录
     * @throws IOException
     **************************************************************************/
    private static void zip(String path,File basePath, ZipOutputStream zo, boolean isZipDir) throws IOException {
        File inFile = new File(path);
        File[] files = new File[0];
        if(inFile.isDirectory()) {    //是目录
            files = inFile.listFiles();
        } else if(inFile.isFile()) {    //是文件
            files = new File[1];
            files[0] = inFile;
        }
        byte[] buf = new byte[1024];
        int len;

        for(int i=0; i<files.length; i++) {
            String pathName = "";
            if(basePath.isDirectory()) {
            	pathName = files[i].getPath().substring(basePath.getPath().length()+1);
            }else{
            	pathName = files[i].getPath().substring(basePath.getParent().length()+1);
            }

            if(files[i].isDirectory()) {
            	
                zip(files[i].getPath(), basePath, zo, isZipDir);
                
            } else {
            	if(!isZipDir){
            		pathName = files[i].getName();
            	}
                FileInputStream fin = new FileInputStream(files[i]);
                zo.putNextEntry(new ZipEntry(pathName));
                while((len=fin.read(buf))>0) {
                    zo.write(buf,0,len);
                }
                fin.close();
            }
        }
    }
   
    public static void main(String[] args) throws IOException{
    	ZipUtils.getZip("C:\\","a.zip", "C:\\upload\\", true);
    }
    
    /**
     * 
     * @param zipPath
     * @param zipName
     * @param isZipDir
     * @param paths  
     * @throws IOException 
     */
    public static File getZip(String zipPath, String zipName,File[] paths) throws IOException {
        File floder=new File(zipPath);
        if(!floder.exists()){
            floder.mkdirs();
        }
        File zip=new File(zipPath,zipName);
        OutputStream os = new FileOutputStream(zip);
        BufferedOutputStream bs = new BufferedOutputStream(os);
        ZipOutputStream zo = new ZipOutputStream(bs);
        zo.setEncoding("gbk");
        zip(paths, zo);
        zo.closeEntry();
        zo.close();
        return zip;
    }
    /**
     * 
     * @param paths
     * @param zo
     * @throws IOException 
     */
    private static void zip(File[] paths, ZipOutputStream zo) throws IOException {
        byte[] buf = new byte[1024];
        int len;
        for(File path:paths){
            if(path.isDirectory()) {
                zip(path.listFiles(), zo);
            } else {
                FileInputStream fin = new FileInputStream(path);
                zo.putNextEntry(new ZipEntry(path.getName()));
                while((len=fin.read(buf))>0) {
                    zo.write(buf,0,len);
                }
                fin.close();
            }
        }
    }
}
