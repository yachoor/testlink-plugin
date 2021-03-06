/* 
 * The MIT License
 * 
 * Copyright (c) 2010 Bruno P. Kinoshita <http://www.kinoshita.eti.br>
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package hudson.plugins.testlink.result.parser.junit.issue9993;

import hudson.plugins.testlink.parser.junit.JUnitParser;
import hudson.plugins.testlink.parser.junit.TestSuite;

import java.io.File;
import java.net.URL;
import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.jvnet.hudson.test.Bug;

/**
 * Tests the JUnit parser for issue 9993.
 * 
 * @author Bruno P. Kinoshita - http://www.kinoshita.eti.br
 * @since 2.5
 */
@Bug(9993)
public class TestIssue9993 
extends TestCase
{
	
	/**
	 * The JUnit parser.
	 */
	private JUnitParser parser;
	
	/**
	 * Initializes the JUnit parser.
	 */
	public void setUp()
	{
		this.parser = new JUnitParser();
	}
	
	public void testParsingNumberOfResultsAndContents()
	{
		Assert.assertEquals(this.parser.getName(), "JUnit");
		
		ClassLoader cl = TestIssue9993.class.getClassLoader();
		URL url = cl.getResource("hudson/plugins/testlink/result/parser/junit/issue9993/MST_DEMO_LOG_01_junit.xml");
		File junitFile = new File( url.getFile() );
		
		List<TestSuite> testSuites = null;
		try
		{
			testSuites = this.parser.parse( junitFile );
		} 
		catch (Exception e)
		{
			Assert.fail("Failed to parse JUnit xml report '"+junitFile+"'.");
		}
		
		Assert.assertNotNull( testSuites );
		
		Assert.assertEquals( testSuites.size(), 1 );
		
		TestSuite testSuite = testSuites.get( 0 );
		
		Assert.assertTrue( testSuite.getFailures() == 0 );
		Assert.assertTrue( testSuite.getErrors() == 0 );
		
		Assert.assertEquals( testSuite.getTestCases().size(), 3 );
		
		Assert.assertEquals( testSuite.getTestCases().get( 0 ).getName(), "UnitTest.Startup");
		
	}
	
	/**
	 * This tests a modified version of the XML result file.
	 */
	public void testParsingNumberOfResultsAndContentsModified()
	{
		Assert.assertEquals(this.parser.getName(), "JUnit");
		
		ClassLoader cl = TestIssue9993.class.getClassLoader();
		URL url = cl.getResource("hudson/plugins/testlink/result/parser/junit/issue9993/MST_DEMO_LOG_01_junit-modified.xml");
		File junitFile = new File( url.getFile() );
		
		List<TestSuite> testSuites = null;
		try
		{
			testSuites = this.parser.parse( junitFile );
		} 
		catch (Exception e)
		{
			Assert.fail("Failed to parse JUnit xml report '"+junitFile+"'.");
		}
		
		Assert.assertNotNull( testSuites );
		
		Assert.assertEquals( testSuites.size(), 2 );
		
		TestSuite testSuite = testSuites.get( 0 );
		
		Assert.assertTrue( testSuite.getFailures() == 0 );
		Assert.assertTrue( testSuite.getErrors() == 0 );
		
		Assert.assertEquals( testSuite.getTestCases().size(), 3 );
		
		Assert.assertEquals( testSuite.getTestCases().get( 0 ).getName(), "UnitTest.Startup");
		
		testSuite = testSuites.get( 1 );
		
		Assert.assertTrue( testSuite.getFailures() == 1 );
		Assert.assertTrue( testSuite.getErrors() == 1 );
		
		Assert.assertEquals( testSuite.getTestCases().size(), 3 );
		
		Assert.assertEquals( testSuite.getTestCases().get( 0 ).getName(), "A");
		Assert.assertEquals( testSuite.getTestCases().get( 0 ).getClassName(), "com.test.qa.A");
		
	}

}
