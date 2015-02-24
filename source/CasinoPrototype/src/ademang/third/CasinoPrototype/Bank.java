package ademang.third.CasinoPrototype;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import android.content.Context;

public class Bank {
	private Context context;
	public Bank(Context context){
		this.context = context;
	}
	/**
	 * Write int data to file
	 * @param data Integer type int
	 * @param fileName Textfile name
	 * @return Success or Fail
	 */
	public boolean fileWrite(int data,String fileName){
		try{
			str2file(context, String.valueOf(data), fileName);
			return true;
		}catch(Exception e){
			return false;
		}
	}
	/**
	 * Write long data to file
	 * @param data Integer type long
	 * @param fileName Textfile name
	 * @return Success or Fail
	 */
	public boolean fileWrite(long data,String fileName){
		try{
			str2file(context, String.valueOf(data), fileName);
			return true;
		}catch(Exception e){
			return false;
		}
	}
	/**
	 * Read int data from file
	 * @param fileName Textfile name
	 * @return Read data
	 */
	public int fileReadforInt(String fileName){
		try{
			String str = file2str(context, fileName);
			return Integer.parseInt(str);
		}catch(Exception e){
			return 0;
		}
	}
	/**
	 * Read long data from file
	 * @param fileName Textfile name
	 * @return Read data
	 */
	public long fileReadforLong(String fileName){
		try{
			String str = file2str(context, fileName);
			return Long.parseLong(str);
		}catch(Exception e){
			return 0;
		}
	}
	
	/**
	 * Transfer of String to byte array
	 * @param context
	 * @param str String
	 * @param fileName Textfile name
	 * @throws Exception
	 */
	private static void str2file(Context context,String str,String fileName) throws Exception{
		data2file(context,str.getBytes(),fileName);
	}
	/**
	 * Transfer of file to String
	 * @param context
	 * @param fileName Textfile name
	 * @return String
	 * @throws Exception
	 */
	private static String file2str(Context context,String fileName) throws Exception{
		byte[] w = file2data(context,fileName);
		return new String(w);
	}
	/**
	 * Transfer of byte array to file
	 * @param context
	 * @param w byte array
	 * @param fileName Textfile name
	 * @throws Exception
	 */
	private static void data2file(Context context,byte[] w,String fileName)throws Exception{
		OutputStream out = null;
		try{
			//open
			out = context.openFileOutput(fileName, Context.MODE_PRIVATE);
			
			//write to file
			out.write(w,0,w.length);
			
			//close
			out.close();
		}catch(Exception e){
			try{
				if(out != null)out.close();
			}catch(Exception e2){
			}
			throw e;
		}
	}
	/**
	 * Transfer of file to byte data
	 * @param context
	 * @param fileName
	 * @return byte data
	 * @throws Exception
	 */
	private static byte[] file2data(Context context,String fileName)throws Exception{
		int size;
		byte[] w = new byte[1024];
		InputStream in = null;
		ByteArrayOutputStream out = null;
		try{
			//open file stream
			in = context.openFileInput(fileName);
			
			//Read byte array
			out = new ByteArrayOutputStream();
			while(true){
				size = in.read(w);
				if(size<=0)break;
				out.write(w,0,size);
			}
			out.close();
			in.close();
			
			//Transfer of ByteArrayOutputStream to byteArray
			return out.toByteArray();
		}catch(Exception e){
			try{
				if(in != null)in.close();
				if(out != null)out.close();
			}catch(Exception e2){
				
			}
			throw e;
		}
	}
}
