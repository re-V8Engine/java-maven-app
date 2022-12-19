@Library('jenkins-shared-library') 
def gv

pipeline {
    agent any
    tools {
        maven "maven-3.8"
    }
    stages {
        stage('init') {
            steps {
                script {
                    gv = load "script.groovy"
                }
            }
        }
        stage('test') {
            steps {
                echo "Executing pipeline for branch ${BRANCH_NAME}"
                echo "Testing application..."
                gv.testApp()
            }
        }
        stage('build jar') {
            when {
                expression {
                    BRANCH_NAME == 'main'
                }
            }
            steps {
                script {
                    buildApp()
                }
            }
        }
        stage('build image') {
            when {
                expression {
                    BRANCH_NAME == 'main'
                }
            }
            steps {
                script {
                    buildImage()
                }
            }
        }
        stage('deploy') {
            when {
                expression {
                    BRANCH_NAME == 'main'
                }
            }
            steps {
                script {
                    echo "Deploying..."
                }
            }
        }
    }
}