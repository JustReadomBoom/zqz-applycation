package com.zqz.service.utils;

import org.apache.http.util.ByteArrayBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 11:17 2020/10/16
 */
public class FileUtil1 {

    private static final Logger log = LoggerFactory.getLogger(FileUtil1.class);


    /**
     * @Author: zqz
     * @Description: 创建文件，若文件夹不存在则自动创建文件夹，若文件存在则删除旧文件
     * @Param: [path]
     * @Return: java.io.File
     * @Date: 11:20 2020/10/16
     */
    public static File createNewFile(String path) {
        File file = new File(path);
        try {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
        } catch (IOException e) {
            log.error("*****创建文件异常:[{}]", e.getMessage(), e);
        }
        return file;
    }

    /**
     * @Author: zqz
     * @Description: 将文件输入流写入文件
     * @Param: [inStream, path]
     * @Return: boolean
     * @Date: 11:21 2020/10/16
     */
    public static boolean writeFileFromInputStream(InputStream inStream, String path) {
        boolean result = true;
        try {
            File file = createNewFile(path);
            FileOutputStream out = new FileOutputStream(file);
            byte[] data = new byte[1024];
            int num;
            while ((num = inStream.read(data, 0, data.length)) != -1) {
                out.write(data, 0, num);
            }
            out.close();
        } catch (Exception e) {
            result = false;
            log.error("*****文件输入流写入异常:[{}]", e.getMessage(), e);
        }
        return result;
    }

    /**
     * @Author: zqz
     * @Description: 获取文件输入流
     * @Param: [path]
     * @Return: java.io.InputStream
     * @Date: 11:21 2020/10/16
     */
    public static InputStream readFileToInputStream(String path) {
        InputStream inputStream = null;
        try {
            File file = new File(path);
            inputStream = new FileInputStream(file);
        } catch (IOException e) {
            log.error("*****获取文件输入流异常:[{}]", e.getMessage(), e);
        }
        return inputStream;
    }


    /**
     * @Author: zqz
     * @Description: 读取文件字节数组
     * @Param: [path]
     * @Return: byte[]
     * @Date: 11:21 2020/10/16
     */
    public static byte[] readFileToBytes(String path) {
        InputStream inputStream = readFileToInputStream(path);
        if (inputStream != null) {
            byte[] data = new byte[1024];
            ByteArrayBuffer buffer = new ByteArrayBuffer(1024);
            int n;
            try {
                while ((n = inputStream.read(data)) != -1) {
                    buffer.append(data, 0, n);
                }
                inputStream.close();
                return buffer.toByteArray();
            } catch (IOException e) {
                log.error("*****读取文件字节数组异常:[{}]", e.getMessage(), e);
            }
        }
        return null;
    }

    /**
     * @Author: zqz
     * @Description: 读取文件内容
     * @Param: [path]
     * @Return: java.lang.String
     * @Date: 11:21 2020/10/16
     */
    public static String readFileToString(String path) {
        byte[] dataBytes = readFileToBytes(path);
        if (dataBytes != null) {
            return new String(dataBytes);
        }
        return null;
    }

    /**
     * @Author: zqz
     * @Description: 以行为单位读取文件，常用于读面向行的格式化文件
     * @Param: [path]
     * @Return: java.lang.String
     * @Date: 11:21 2020/10/16
     */
    public static String readFileByLines(String path) {
        File file = new File(path);
        if (!file.exists() || file.isDirectory()) {
            return null;
        }
        StringBuffer buffer = new StringBuffer();
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        try {
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
            String tempString;
            while ((tempString = bufferedReader.readLine()) != null) {
                buffer.append(tempString).append(System.getProperty("line.separator"));
            }
        } catch (Exception e) {
            log.error("*****取文件异常:[{}]", e.getMessage(), e);
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (fileReader != null) {
                    fileReader.close();
                }
            } catch (Exception pe) {
                pe.printStackTrace();
            }
        }
        return buffer.toString();
    }

    /**
     * 根据给出路径自动选择复制文件或整个文件夹
     *
     * @param src  :源文件或文件夹路径
     * @param dest :目标文件或文件夹路径
     */
    public static void copyFiles(String src, String dest) {
        try {
            File srcFile = new File(src);
            if (srcFile.exists()) {
                if (srcFile.isFile()) {
                    writeFileFromInputStream(readFileToInputStream(src), dest);
                } else {
                    File[] subFiles = srcFile.listFiles();
                    if (subFiles.length == 0) {
                        File subDir = new File(dest);
                        subDir.mkdirs();
                    } else {
                        for (File subFile : subFiles) {
                            String subDirPath = dest + System.getProperty("file.separator") + subFile.getName();
                            copyFiles(subFile.getAbsolutePath(), subDirPath);
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("*****复制文件异常:[{}]", e.getMessage(), e);
        }
    }

    /**
     * 根据给出路径自动选择删除文件或整个文件夹
     *
     * @param path :文件或文件夹路径
     */
    public static void deleteFiles(String path) {
        try {
            File file = new File(path);
            if (!file.exists()) {
                return;
            }
            if (file.isFile()) {
                file.delete();// 删除文件
            } else {
                File[] subFiles = file.listFiles();
                for (File subfile : subFiles) {
                    deleteFiles(subfile.getAbsolutePath());// 删除当前目录下的子目录
                }
                file.delete();// 删除当前目录
            }
        } catch (Exception e) {
            log.error("*****删除文件异常:[{}]", e.getMessage(), e);
        }
    }

    /**
     * @Author: zqz
     * @Description: NIO方式读取文件至byte数组
     * @Param: [filePath]
     * @Return: byte[]
     * @Date: 11:18 AM 2020/8/17
     */
    public static byte[] readFile2ByteArrayNIO(String filePath) throws Exception {
        FileChannel channel = null;
        FileInputStream fs = null;
        File f = new File(filePath);
        if (!f.exists()) {
            throw new RuntimeException(String.format("模板文件(%s)不存在", filePath));
        }
        try {
            fs = new FileInputStream(f);
            channel = fs.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
            while ((channel.read(byteBuffer)) > 0) {
                // do nothing
                // System.out.println("reading");
            }
            return byteBuffer.array();
        } catch (Exception e) {
            log.error("Read file error:[{}]", e.getMessage());
            throw new RuntimeException(null == e.getMessage() ? "读取文件异常" : e.getMessage());
        } finally {
            try {
                if (null != channel) {
                    channel.close();
                }
                if (null != fs) {
                    fs.close();
                }
            } catch (Exception e) {
                log.error("关闭流异常:[{}]", e.getMessage(), e);
            }
        }
    }

    public static void main(String[] args) {
        String filePath = "/Users/zhouqizhi/Desktop/公司资料.txt";
        String lines = readFileByLines(filePath);
        log.info("content={}", lines);
    }


}
