pipeline {
	agent any

    tools {
		// Ensure Docker is available
    docker 'docker'
	}


    environment {
		SELENIUM_URL = 'http://selenium-chrome:4444/wd/hub'  // Container ismi ile erişim
        MAVEN_IMAGE = 'maven:3.9.2-jdk-17'
    }

    stages {
		stage('Start Selenium Standalone Chrome') {
			steps {
				script {
					sh '''
                        docker rm -f selenium-chrome || true
                        docker network create selenium-net || true
                        docker run -d --name selenium-chrome --network selenium-net -p 4444:4444 selenium/standalone-chrome:latest
                    '''
                    sleep 10
                }
            }
        }

        stage('Checkout Code') {
			steps {
				checkout scm
            }
        }

        stage('Run Maven Tests') {
			steps {
				timeout(time: 30, unit: 'MINUTES') {
					retry(2) {
						script {
							docker.image(MAVEN_IMAGE).inside("--shm-size=2g -v ${env.WORKSPACE}:/app -w /app --network selenium-net") {
								sh "mvn clean verify -Dcucumber.filter.tags=@chrome -Dselenium.url=${SELENIUM_URL}"
                    }
                }
            }
        }
    }
}


        stage('Stop Selenium Container') {
			steps {
				script {
					sh 'docker rm -f selenium-chrome || true'
                    sh 'docker network rm selenium-net || true'
                }
            }
        }
    }

    post {
		always {
			echo 'Cleaning up...'
        sh 'docker rm -f selenium-chrome || true'
        sh 'docker network rm selenium-net || true'
        junit '**/target/surefire-reports/*.xml'
        cucumber '**/target/cucumber-reports/*.json'
    }
}

}