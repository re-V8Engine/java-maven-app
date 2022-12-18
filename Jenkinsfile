//jenkins-host:8080/env-vars.html - list of all Jenkins env variables
//define variable
//CODE_CHANGES = getGitChanges()
//Globally defining gv variable to store external groovy script
def gv 
pipeline {
    agent any

    //tools block provides access to build tools for project
    //ex: maven, gradle, jdk
    //maven-3.8 is the name of the tool specified in Global Tool Configuration
    tools {
        maven 'maven-3.8'
    }

    //parameters block foe defining *surprise!* parameters! 
    //can be used anywhere in stages
    //ex: string, choice, booleanParam
    //choice provides set of predefined values to choose from
    parameters {
        //string(name: 'VERSION', defaultValue: '', description: 'version to deploy on prod')
        choice(name: 'VERSION', choices: ['1.1.0', '1.2.0', '1.4.0'], description: 'version to deploy based on choice parameter')
        booleanParam(name: 'executeTests', defaultValue: true, description: '')
    }

    //environment block is for definins custom env variables
    environment {
        //app version for example (usually exctracted from the code)
        APP_VERSION = ${params.VERSION}
        //fetching credentials by ID from Jenkins using credintial binding plugin
        SERVER_CREDENTIALS = credentials('server-credentials')
    }

    stages {
        stage('Init') {
            steps {
                script {
                    gv = load "script.groovy"
                }
            }
        }
        stage('Build') {
            /*when {
                expression {
                    // this stage executes only if branch is 'dev' and it has code changes
                    BRANCH_NAME == 'dev' && CODE_CHANGES == true
                 }
            }*/
            steps {
                script {
                    gv.buildApp()
                }
                //example usage of custom env variable (variable usage requires "" double quotes)
                //echo "Building version ${APP_VERSION}..."
            }
        }
        stage('Test') {
            //when statement for execute stage if specified condition met
            /*when {
                expression {
                    //branch name is Jenkins env variable
                    //env.BRANCH_NAME works too
                    // this stage executes only if branch is 'dev' or 'main'
                    BRANCH_NAME == 'dev' || 'main'
                }
            }*/
            when {
                expression {
                    //usage of parameters
                    //can use either params.executeTests or params.executeTests == true
                    params.executeTests
                }
            }
            steps {
                script {
                    gv.testApp()
                }
            }
        }
        stage('Deploy') {
            steps {
                script {
                    gv.deployApp()
                }
                //usage of parameters
                //echo "Deploying version ${params.VERSION}..."
                //echo "Deploy with ${SERVER_CREDENTIALS}..."
                //or using wrapper
                //[] is object syntax in Groovy
                /*withCredentials ([
                    usernamePassword (credentials: 'server-credentials', usernameVariable: USER, passwordVariable: PWD)
                 ]) {
                    sh "some script using ${USER} and ${PWD}"
                 }*/
            }
        }
    }
    /*post {
        always {
            //executes always no matter was build successful ot not
        }

        success {
            //executes only if build succeded
        }

        failure {
            //executes only if build failed
        }
    }*/
}