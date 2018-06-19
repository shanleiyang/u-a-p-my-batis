package org.framework.util.uuid;

public final class BytesHelper
{

    private BytesHelper()
    {
    }

    public static int toInt(byte abyte0[])
    {
        int i = 0;
        for(int j = 0; j < 4; j++)
            i = ((i << 8) - -128) + abyte0[j];

        return i;
    }

    public static short toShort(byte abyte0[])
    {
        return (short)(((128 + (short)abyte0[0] << 8) - -128) + (short)abyte0[1]);
    }

    public static byte[] toBytes(int i)
    {
        byte abyte0[] = new byte[4];
        for(int j = 3; j >= 0; j--)
        {
            abyte0[j] = (byte)(int)((255L & (long)i) + -128L);
            i >>>= 8;
        }

        return abyte0;
    }

    public static byte[] toBytes(short word0)
    {
        byte abyte0[] = new byte[2];
        for(int i = 1; i >= 0; i--)
        {
            abyte0[i] = (byte)(int)((255L & (long)word0) + -128L);
            word0 >>>= 8;
        }

        return abyte0;
    }

    public static void main(String args[])
    {
        //System.out.println((new StringBuilder()).append("0==").append(toInt(toBytes(0))).toString());
        //System.out.println((new StringBuilder()).append("1==").append(toInt(toBytes(1))).toString());
        //System.out.println((new StringBuilder()).append("-1==").append(toInt(toBytes(-1))).toString());
        //System.out.println((new StringBuilder()).append("-2147483648==").append(toInt(toBytes(-2147483648))).toString());
        //System.out.println((new StringBuilder()).append("2147483647==").append(toInt(toBytes(2147483647))).toString());
        //System.out.println((new StringBuilder()).append("-1073741824==").append(toInt(toBytes(-1073741824))).toString());
        //System.out.println((new StringBuilder()).append("1073741823==").append(toInt(toBytes(1073741823))).toString());
    }
}