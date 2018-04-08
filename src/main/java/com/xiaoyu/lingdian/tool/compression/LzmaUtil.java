package com.xiaoyu.lingdian.tool.compression;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import com.xiaoyu.lingdian.tool.compression.LZMA.Zipper;
/**
 * 测试类，包含encode和decode函数，此例为将文件分割成块再对每块压缩的示例。
 *
 */
public class LzmaUtil {

    /**
     * 测试函数
     */
    public void work() throws Exception {
        Zipper zipper = new Zipper();

        //读取文件
        File infile = new File("/home/find/ddown/aa/aa.pptx");
        BufferedInputStream ins = new BufferedInputStream(new FileInputStream(infile));
        BufferedOutputStream outs = new BufferedOutputStream(new FileOutputStream(new File("/home/find/ddown/aa/aa.lzma")));
        // @todo 设置real_len为int，实际限制了每块的大小不能超过2GB
        int real_len;
        // 要将文件分割的文件块的大小
        final int blockSize = 1024 << 3;
        // 用来保存每块压缩大小
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < infile.length(); i += real_len) {
            //由于压缩可能会导致文件块变大，因此预开辟两倍空间存放，默认文件分块大小为8KB，即1024<<3
            byte[] inbytes = new byte[blockSize << 1];
            // @todo: 如果实际不是读到1024 × 8，除非到文件尾部，否则应该继续读取，直到读完1024*8长度的块。
            real_len = ins.read(inbytes, 0, blockSize);
            // @warning: 一定要注意，要以实际大小建stream！！！否则压缩时，会将实际有效数据后面的部分空数据也认为是有效的。！！！
            ByteArrayInputStream inputStream = new ByteArrayInputStream(inbytes, 0, real_len);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream(blockSize << 1);
            System.out.print("实际读取的字节数" + real_len);
            zipper.encode(inputStream, outputStream, (long) real_len);
            // ByteArrarInputStream.size()是指实际有效数据
            queue.offer(outputStream.size());
            System.out.println("压缩后大小" + (outputStream.size()));
            //将压缩好的数据写入压缩文件
            outs.write(outputStream.toByteArray());
        }
        System.out.println("encode end\n======================================\n");
        // 最后一定不要忘记flush
        outs.flush();
        outs.close();
        ins.close();

        // decoder part
        infile = new File("/home/find/ddown/aa/aa.lzma");
        BufferedOutputStream o2 = new BufferedOutputStream(new FileOutputStream(new File("/home/find/ddown/aa/aa_extra.pptx")));
        BufferedInputStream i2 = new BufferedInputStream(new FileInputStream(infile));
        // 每个压缩块的大小都在queue里。一个一个压缩块的进行读取和解压
        while (!queue.isEmpty()) {
            byte[] inbytes = new byte[blockSize << 1];
            real_len = i2.read(inbytes, 0, queue.peek());
            //@todo: 这里应该throw error
            if (real_len != queue.peek()) {
                System.out.println("读取的大小和队列里的大小（要读的大小）不同" + real_len + "\t" + queue.peek());
            }

            ByteArrayInputStream inputStream = new ByteArrayInputStream(inbytes, 0, queue.peek());
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream(blockSize << 1);
            zipper.decode(inputStream, outputStream);
            o2.write(outputStream.toByteArray());

            queue.poll();
        }
        o2.flush();
        o2.close();
        i2.close();
    }

    public static void main(String[] args) {
        LzmaUtil example = new LzmaUtil();
        try {
            example.work();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
