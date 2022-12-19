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
                    buildApp 'v8engine/java-maven-app:1.4.0'
                }
            }
        }
        stage('build image') {
            steps {
                script {
                    buildImage()
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
