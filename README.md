<img src="readme_img/logo.png" alt="Logo" style="width: 200px;"/>

# amos-ss17-proj7 Virtual Ledger

[![Build Status](https://travis-ci.org/BankingBoys/amos-ss17-proj7.svg?branch=master)](https://travis-ci.org/BankingBoys/amos-ss17-proj7) [![codecov](https://codecov.io/gh/BankingBoys/amos-ss17-proj7/branch/dev/graph/badge.svg)](https://codecov.io/gh/BankingBoys/amos-ss17-proj7)


## Installation general

You need an environment variable called VIRTUAL_LEDGER_DB_PASSWORD that contains the password for the database.
Restarting the computer/server may be required after setting it!


### Install workspace for eclipse

Open terminal in the rootproject and run

	gradle eclipse

Go to eclipse

1. import
2. existing projects into workspace
3. select root-directory of this project as root directory
4. select "Search for nested projects"
5. select all projects
6. click "Finish"


### Install workspace for IDEA

Open terminal in the rootproject and run

	gradle idea

Then import the project in idea.

### Use Jetty-Server

Open Terminal in server-workspace.

Run the command

	gradle clean bootRun

you build the server-war-file and run it on an embedded jetty server.

## Build spring boot application

Open terminal in the rootproject and run:

	gradle clean build
	
The compiled jar-file can be found in build/libs 

## Use test coverage

### Local test coverage 

To compute the test coverage of all projects run:

	gradle clean check
	
The html-testcoverage is in `$projectname/build/jacocoHtml`

E.g. for the serverproject in `server/build/jacocoHtml`

### Global test coverage

Visit [CodeCov](https://codecov.io/gh/BankingBoys/amos-ss17-proj7)
	
