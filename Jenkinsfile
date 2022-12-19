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
                script {
                    gv.testApp()
                }
            }
        }
        stage('build jar') {
            steps {
                script {
                    buildApp()
                }
            }
        }
        stage('build image') {
            steps {
                script {
                    buildImage 'v8engine/java-maven-app:1.4.1'
                }
            }
        }
        stage('deploy') {
            steps {
                script {
                    echo "Deploying..."
                }
            }
        }
    }
}
