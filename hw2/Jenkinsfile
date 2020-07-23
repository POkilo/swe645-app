pipeline {
    environment {
        registry = "swe645docker/swe645-group-project"
        registryCredential = 'dockerhub'
        dockerImage = ''
    }
    agent any
    
    stages {
        stage('Cloning Git') {
            steps{
                git 'https://github.com/JuliaHsu/SWE645-group-project.git'
                withAnt(installation: 'Ant1.10.7') {
                        sh'''
                        #!/bin/bash
                        cd ~/workspace/swe645-group-project/swe645-group
                        ls
                        ant war
                        '''
                }
            }
        }

        stage('Build') {
            steps {
                echo 'Building..'
                script {

                  dockerImage = docker.build registry + ":$BUILD_NUMBER"
                }

            }
        }
        stage('Test') {
            steps {
                echo 'Testing..'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }

        stage('Deploy Image') {
            steps{
                script{
                    docker.withRegistry('',registryCredential){
                        dockerImage.push()
                    }
                }
            }
        }

        stage('Remove Unused docker image') {
          steps{
            sh "docker rmi $registry:$BUILD_NUMBER"
          }
        }
		
		stage('redeploy') {
            steps{
               
               sh'''
               #!/bin/bash
                docker login
                docker pull swe645docker/swe645-group-project:$BUILD_NUMBER
                sudo -s source /etc/environment
                kubectl --kubeconfig /home/ubuntu/.kube/config set image deployment swe645 swe645-group=docker.io/swe645docker/swe645-group-project:$BUILD_NUMBER
            '''
            }
        }

        stage('Remove Unused docker image') {
          steps{
            sh "docker rmi $registryRestful:$BUILD_NUMBER"
            sh "docker rmi $registryApp:$BUILD_NUMBER"
          }
        }
		
    }

     
}

