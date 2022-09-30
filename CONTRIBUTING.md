Looking to report an issue/bug or make a feature request? Please refer to the [README file](https://github.com/Raj-m01/News-App/blob/master/README.md).

---

Thanks for your interest in contributing!

# Code contributions

If you're interested in taking on [an open issue](https://github.com/Raj-m01/News-App/issues), please comment on it so others are aware.
You do not need to ask for permission nor an assignment. Be sure to include a **title, clear description** and **test case** demonstrating the new feature you want to add to the project.

## Prerequisites

Before you start, please note that the ability to use following technologies is **required** and that existing contributors will not actively teach them to you.

- [Basic Android development](https://developer.android.com/)
- [Kotlin](https://kotlinlang.org/)
- [Android Studio](https://developer.android.com/studio)
- Emulator or phone with developer options enabled to test changes.


## Steps to contribue

Here are the simple steps for contributing to this repo:

Firstly fork the repo to your own GitHub account by clicking the Fork button on the top-right corner.
After the successful fork, you'll acknowledge a copy of this repo on your own.
 
1. **Clone the repo**
  
      - Now it's time to copy this repo to my own laptop/PC. To clone the repo you can write the below command in **Git Bash**

              > git clone <LINK_OF_FORKED_REPO_IN_YOUR_ACCOUNT>

        OR 

        You can get the repo link from the Download section of forked repo in your account.


2. **Set up remote repo**

      - When you clone your fork, it will automatically set your fork as the "origin" remote. Use git remote -v to show your current remotes. You should see the URL of your fork (which you copied in step 3) next to the word "origin". 
      If you don't see the "origin" remote, you can add it using the git command below.

              > git remote add origin <LINK_OF_FORKED_REPO_IN_YOUR_ACCOUNT>

      - Now you have to set up the upstream. write the following Git command to set up the upstream.
      > git remote add upstream https://github.com/Raj-m01/News-App.git

      - Now pull the latest changes from original repo to your local changes by firing the below command
              > git pull upstream master
  
  
3. **It's Code Time now**
  
      After getting the project in code editor, make necessary changes.


4. **Now it's time to save the work**

      - Make a branch for the feature you have worked on
        > git checkout -b feature_branch_name
      - Stage the changes you have made by firing the below command
        > git add .
      - Commit the changes 
        > git commit -m "Description of changes in your work"
      - Push the changes to your forked repo in that specific feature branch
        >  git push origin feature_branch_name
        
    Note: Create a branch for that feature and push there. No direct push to master. 
        
        
5. **Let's finish this**
    - Go to your forked repo on GitHub website and refresh the page,

    - Click on pull-request and you will be redirected to another page where you will see something like below image 

    - After that you have to write your GitHub username as the title of your pull-request and describe your work if you want and that's it!! Create a pull-request by clicking the button
    Also add the below 2 lines in the description. It is compulsory for successful submission.

    ![Screenshot 2021-09-26 at 6 58 08 PM](https://user-images.githubusercontent.com/58077762/94461826-43fab680-01d8-11eb-96cd-80b2d69e13be.png)


     - [X] I have read the Code Of Conduct.

     - [X] I have followed all the steps of submission properly.


**Woohoo!! Congratulations on making your open source contribution🎉**
**Wait for some time to get your PR merged by our team**
