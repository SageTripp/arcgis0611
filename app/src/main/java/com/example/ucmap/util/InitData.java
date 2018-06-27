package com.example.ucmap.util;

import android.content.res.AssetManager;
import android.os.Environment;
import android.os.StatFs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class InitData {

	private static String MAP_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()+"/yzt";

	public InitData(AssetManager assetManager,ICallback callback) {
		unZipReplaceData(assetManager,callback);
	}

	private void unZipReplaceData(AssetManager assetManager ,ICallback callback){
		try {
			InputStream dataSource = assetManager.open("demo.zip");
			File file=new File(MAP_PATH);
			if(file.exists()) file.delete();
			unzip(dataSource,MAP_PATH,callback);
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	private static void unzip(InputStream zipFileName, String outputDirectory, ICallback callback) {
		try {
			ZipInputStream in = new ZipInputStream(zipFileName);
			ZipEntry entry = in.getNextEntry();
			File file = new File(outputDirectory);
			file.mkdir();
			byte[] buf = new byte[1024];
			long unzipSize = 0;
			long tmpCount = 0;
			while (entry != null) {
				if (entry.isDirectory()) {
					String name = entry.getName();
					name = name.substring(0, name.length() - 1);
					String dirstr = outputDirectory + File.separator + name;
					file = new File(dirstr);
					file.mkdir();
				} else {
					String dirstr = outputDirectory + File.separator + entry.getName();
					file = new File(dirstr);
					file.createNewFile();
					FileOutputStream out = new FileOutputStream(file);
					int readLen = 0;
					while ((readLen = in.read(buf)) > 0) {
						out.write(buf, 0, readLen);
						tmpCount += readLen;
						if (tmpCount / (1024 * 1024) > 1) {
							unzipSize += tmpCount;
							callback.callback(unzipSize);
							tmpCount = 0;
						}
					}
					out.close();
				}
				entry = in.getNextEntry();
			}
			in.close();
			callback.onSuccess();
		} catch (Exception e) {
			callback.onFaile();
		}
	}

}
