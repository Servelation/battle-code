package ru.nechay.practice.battlecode.logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProgramHandler {
	private final String absoluteFilePath ="C://Users//Alex//Desktop//server";
	private File fileJava;
	private File outFile;
	private String program;
	private String className;
	private String uuidString;
	private String outStreamFileName;
	private String input;
	
	public ProgramHandler(String program, String input) {
		this.program = program;
		this.input = input;
		 // Creating a random UUID (Universally unique identifier).
        UUID uu = UUID.randomUUID();
        this.uuidString = uu.toString();
//        создаем java файл с такой программой
        this.fileJava = new File(absoluteFilePath+"//"+uuidString+".java");
        try {
			if(fileJava.createNewFile()){
				System.out.println(absoluteFilePath + " Файл создан");
			} else {
			    System.out.println("Файл " + absoluteFilePath + " уже существует");
			}
		} catch (IOException e) {
			System.out.println("Нет такого пути");
			e.printStackTrace();
		}
	}
	public boolean isValid() {
		Pattern pat = Pattern.compile("class\\W+(\\w+)");
        Matcher m = pat.matcher(this.program);
        this.className ="";
        if(m.find()) {
        	this.className = m.group(1);
        	return true;
        }else {
        	return false;
        }
		
	}
	public void redirectionOfStreams() {
		String construction = "public static void main(String[] args) {";
		int offset = this.program.indexOf(construction)+construction.length();
		StringBuffer newProg = new StringBuffer(program);
		this.outStreamFileName = absoluteFilePath+"//out"+uuidString+".txt";
		this.outFile = new File(this.outStreamFileName);
		try {//создаем выходной файл
			if(outFile.createNewFile()){
				System.out.println("outFile создан");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String outConstruction= " PrintStream out = new PrintStream(new FileOutputStream(\"" +outStreamFileName +"\"));\r\n"
				+ "    PrintStream err = new PrintStream(new FileOutputStream(\""+outStreamFileName +"\"));\r\n"
				+ "    System.setOut(out);\r\n"
				+ "    System.setErr(err);\r\n  ";
		newProg.insert(offset, outConstruction);
		String importConstruction="  import java.io.FileNotFoundException;\r\n"
				+ "import java.io.FileOutputStream;\r\n"
				+ "import java.io.PrintStream;\r\n"
				+ "    ";
		newProg.insert(0, importConstruction);
		
		
		construction = "public static void main(String[] args)";
		offset = newProg.indexOf(construction)+construction.length();
		String throwsConstruction = " throws FileNotFoundException ";
		newProg.insert(offset, throwsConstruction);
		
		this.program = newProg.toString();
		this.program = this.program.replace("System.in", "\""+ this.input+"\"" );
	}
	
	public void printProgramToTheFile() {
		 try(FileWriter writer = new FileWriter(this.fileJava, false)){
	        	writer.write(program);
	        	writer.flush();
	        } catch (IOException e) {
				e.printStackTrace();
			}
	}
	public void workAtTheShell() {
		 File fileOut = new File(absoluteFilePath+"//"+"out"+".txt");   
		 try{
	        	String[] command ={"cmd"};
	        	Process proc = Runtime.getRuntime().exec(command);
	        	new Thread(new SyncPipe(proc.getErrorStream(), System.err)).start();
	        	new Thread(new SyncPipe(proc.getInputStream(), new FileOutputStream(fileOut))).start();
	        	PrintWriter stdin = new PrintWriter(proc.getOutputStream());
//	        	stdin.println("echo %cd%");
	        	stdin.println("cd ..");
	        	stdin.println("cd c://Users//Alex//Desktop//server");
	        	stdin.println("javac "+fileJava.getName());
	        	stdin.println("java -classpath . "+className);
	        	stdin.close();
	            proc.waitFor();
	            proc.destroy();
	        }
	        catch (IOException e)
	        {
	            e.printStackTrace();
	        }
	        catch (InterruptedException e)
	        {
	            e.printStackTrace();
	        }
	}
	public String getOutputOfTheProgram() throws IOException {
		return Files.lines(Paths.get(outFile.getAbsolutePath()))
				.reduce((sum,x) -> sum+x)
				.get();	
	}
	public void flush() {
		 if(fileJava.delete()) {
         	System.out.println("Файл .java удален");
         };
         if(outFile.delete()) {
          	System.out.println("Файл вывода удален");
         };
         File fileClass = new File(absoluteFilePath+"//"+className+".class");
         if(fileClass.delete()) {
           	System.out.println("Файл .class удален");
         };
	}
	
	
	public File getFileJava() {
		return fileJava;
	}
	public void setFileJava(File fileJava) {
		this.fileJava = fileJava;
	}
	public String getProgram() {
		return program;
	}
	public void setProgram(String program) {
		this.program = program;
	}
	public String getAbsoluteFilePath() {
		return absoluteFilePath;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getUuidString() {
		return uuidString;
	}
	public void setUuidString(String uuidString) {
		this.uuidString = uuidString;
	}
	public String getOutStreamFileName() {
		return outStreamFileName;
	}
	public void setOutStreamFileName(String outStreamFileName) {
		this.outStreamFileName = outStreamFileName;
	}
	public File getOutFile() {
		return outFile;
	}
	public void setOutFile(File outFile) {
		this.outFile = outFile;
	}
	public String getInput() {
		return input;
	}
	public void setInput(String input) {
		this.input = input;
	}


}
