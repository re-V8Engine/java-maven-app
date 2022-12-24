#!/usr/bin/env groovy

library identifier: 'jenkins-shared-library@main', retriever: modernSCM(
    [$class: 'GitSCMSource',
     remote: 'https://github.com/re-V8Engine/jenkins-shared-library.git',
     credentials: 'github-credentials'
    ]
)
def gv

pipeline {
    agent any
    tools {
        maven "maven-3.8"
    }
    environment {
        DOCKER_IMAGE_NAME = 'v8engine/java-maven-app:1.4.1'
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
                    dockerBuild(${DOCKER_IMAGE_NAME})
                }
            }
        }
        stage('push image') {
            steps {
                script {
                    dockerLogin('dockerhub-credentials')
                    dockerPush(${DOCKER_IMAGE_NAME})
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
