package org.framework.util.uuid;

import java.io.Serializable;
import java.net.InetAddress;

public class UUIDGenerator {
	private String sep;
    private int length;
    private static final int IP;
    private static short counter = 0;
    private static final int JVM = (int)(System.currentTimeMillis() >>> 8);
    private static int mycount = 0;

    static 
    {
        int i;
        try
        {
            i = BytesHelper.toInt(InetAddress.getLocalHost().getAddress());
        }
        catch(Exception exception)
        {
            i = 0;
        }
        IP = i;
    }
	
    public UUIDGenerator()
    {
        sep = "";
        length = 32;
    }

    public int getLength()
    {
        return length;
    }

    public void setLength(int i)
    {
        length = i;
    }

    public String getNextSeqId()
    {
        setLength(32);
        return (new StringBuffer(36)).append(format(getIP())).append(sep).append(format(getJVM())).append(sep).append(format(getHiTime())).append(sep).append(format(getLoTime())).append(sep).append(format(getCount())).toString();
    }

    public Serializable getNextSeqId(int i)
    {
        setLength(i);
        mycount++;
        StringBuffer stringbuffer = new StringBuffer(36);
        stringbuffer.append(format(getIP())).append(sep).append(format(getJVM())).append(sep).append(format(getHiTime())).append(sep).append(format(getLoTime())).append(sep).append(format(getCount())).toString();
        return processLength(stringbuffer);
    }

    protected int getJVM()
    {
        return JVM;
    }

    protected short getCount()
    {
        java.lang.Object local = UUIDGenerator.class;
        synchronized (local) {
        	if(counter < 0)
        		counter = 0;
        	return counter++;
		}
    }

    protected int getIP()
    {
        return IP;
    }

    protected short getHiTime()
    {
        return (short)(int)(System.currentTimeMillis() >>> 32);
    }

    protected int getLoTime()
    {
        return (int)System.currentTimeMillis();
    }

    protected String format(int i)
    {
        String s = Integer.toHexString(i);
        StringBuffer stringbuffer = new StringBuffer("00000000");
        stringbuffer.replace(8 - s.length(), 8, s);
        return stringbuffer.toString();
    }

    protected String format(short word0)
    {
        String s = Integer.toHexString(word0);
        StringBuffer stringbuffer = new StringBuffer("0000");
        stringbuffer.replace(4 - s.length(), 4, s);
        return stringbuffer.toString();
    }

    private String processLength(StringBuffer stringbuffer)
    {
        if(getLength() != 32)
        {
            int i = 32 - getLength();
            if(i > 0)
            {
                stringbuffer.delete(0, i);
            } else
            {
                stringbuffer.insert(0, sep);
                stringbuffer.insert(0, random(i));
            }
        }
        return stringbuffer.toString();
    }

    private String random(int i)
    {
        long l = System.currentTimeMillis();
        String s = Long.toHexString((long)mycount + l);
        StringBuffer stringbuffer = new StringBuffer();
        for(int j = i; j < 0; j++)
            stringbuffer.append("0");

        if(-i < 8)
            stringbuffer.replace(0, -i, s.substring(s.length() + i));
        else
        if(-i > 16)
        {
            stringbuffer.replace(0, 8, s.substring(s.length() - 8));
            s = Long.toHexString(l + (long)mycount + (long)getCount());
            stringbuffer.replace(8, 16, s.substring(s.length() - 8));
        } else
        {
            stringbuffer.replace(0, 8, s.substring(s.length() - 8));
            s = Long.toHexString(l + (long)mycount + (long)getCount() + l / 10000L);
            stringbuffer.replace(8, -i, s.substring(s.length() + i + 8));
        }
        return stringbuffer.toString();
    }

}
