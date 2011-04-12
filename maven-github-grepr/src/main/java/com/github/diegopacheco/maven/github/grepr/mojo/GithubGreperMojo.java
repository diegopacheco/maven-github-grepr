package com.github.diegopacheco.maven.github.grepr.mojo;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.eclipse.jgit.pgm.Main;

/**
 * This mojo goes to github and download the maven project sources complile it and build it.
 * @autor diegopacheco
 * @version 1.0-SNAPSHOT
 * @since 12/04/2011
 * 
 * @goal grep 
 */
public class GithubGreperMojo extends AbstractMojo {
	
	/**
	 * @parameter 
	 */	
	private String githubUrl;	
	private static final String TEMP_DIR = "C:/tmp/gittemp/";
	
	public void execute() throws MojoExecutionException {
		getLog().info("Starting to greping github project: " + githubUrl);
		grepGithubSources();
		complileInstallMaven();
		getLog().info("DONE! Project: " + githubUrl + " greped and installed ");
	}

	//TODO let the user specify the TEMP_DIR via maven parameter
	//TODO let the user decide if should fall when repository existe or should be removed, via maven parameter
	private void grepGithubSources() {
		try {
			Main.main(new String[]{"--git-dir", TEMP_DIR + ".git", "clone",githubUrl});			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	//TODO detected os and do different for windows and linux
	//TODO make the maven console output appear
	//TODO let the user pass maven argumets via maven parameter
	private void complileInstallMaven() {
		try {
			Runtime.getRuntime().exec("C:\\Windows\\System32\\cmd.exe /K /A mvn install -f " + TEMP_DIR + "pom.xml");			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	//TODO this must be deleted and must be tests for this.
	public static void main(String[] args) throws Throwable {
		GithubGreperMojo ggm = new GithubGreperMojo();
		ggm.setGithubUrl("git://github.com/phunt/avro-maven-plugin.git");
		ggm.execute();
	}

	public String getGithubUrl() {
		return githubUrl;
	}
	public void setGithubUrl(String githubUrl) {
		this.githubUrl = githubUrl;
	}	
}