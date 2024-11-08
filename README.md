
<br><br><br>
<b>Welcome to Ben's Comparison tool</b><br><br>
I have found that many many times I need to compare 2 Lists and there is always a catch or trick like duplicate values
that causes it to take a lot longer than it should.<br>
I wanted to create something super quick and so simple even an idiot like Ben (that's me) can use AND works in most cases. 
<br>
The design goal was to make it easy enough that it should take less than  10 minutes to figure out how
it works and once you know that, it will take less than 5 minutes to run it next time.
<br>
I also wanted to keep it pure Java in case anyone wanted to use it their projects without adding dependencies.
<br>
The program works with Java Collections but for displays I choose to use the word "List" because that is what most
people understand. I want the result be readable by non-techies.
<br>
<br>

<b>GETTING STARTED</b><br>
1. Goto the "test" folder.
2. The "MainManualRunner" is the simplest example of how to run the program. You can run that...
3. Goto the "examples" folder under test... This will have more complex examples including how to load from a CSV file.
4. The "examples" have parameters at the top, just fill those in and run.
5. You can take the "examples" and put them directly in your project, you might need include the "TEST" Maven dependencies. 

<br>
<b>Best Practices:</b>
* The names of the lists should not be more than 25 characters. This is only for display and file name reasons. There
is no actual length enforced. 
* Always include a report directory. There are A LOT of useful reports.
* Let the program handle blanks separately. (The default setting)


<br><br><br><br><br><br>
<b>TODO:</b>
* Include the location of the file reports in the console report.
* Include sample reports as examples to explain how it works.
* Convert the input values to an object. (DO NOT REQUIRE THAT TO RUN, JUST INTERNALLY)
* Handle nulls for input values (names and sets)
* Add recommended length to Input List Names.
* https://opensource.stackexchange.com/questions/4710/how-to-publish-my-open-source-library-to-maven
* When the program starts... if using reports, check to see if any of the existing reports are open in Excel (aka: locked)
* Add example on how to compare 2 columns in an Excel File.
* Add "dual" modes. That would do the compare WITH and WITH some options like trim. So, you would get 2 sets of reports. I could call it "insight"
* I can put "Enhanced" reports that use CSV in the test code???? That way, I can use a CSV Parser but also keep the main code pure java.
* 



<br>
Compare:<br>
* Exclude List
* Might want to change variable names to indicate what they do... put "normalize" as part of trim and ignore case


<br>
<br>
Report:<br>
* Include BOM in reports. Might want to make this configurable.
* FINISH ALL the File reports! (Each Collection should have it's own File.
* Might want to zip up the final reports Dir.
* Values changed by Normalization
* For CSV reports... return a report as a List&lt;List&lt;String&gt;&gt; instead of writing to file, so I do all the heavy lifting of report generation but not include a dependency.


<br><br><br>