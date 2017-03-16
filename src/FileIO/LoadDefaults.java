package FileIO;

import java.io.File;

public class LoadDefaults
{
	private String location = System.getenv("APPDATA")+"\\simEngine\\settings.ini";
	
	public LoadDefaults()
	{
		File f = new File(location);
		
		if(f.exists())
		{
			
		}else
		{
			
		}
	}
}