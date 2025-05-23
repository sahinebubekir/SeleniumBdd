pipeline {
	agent any

    environment {
		SELENIUM_URL = 'http://selenium-chrome:4444/wd/hub'
    }

    stages {
		stage('Checkout') {
			steps {
				checkout scm
            }
        }

        stage('Setup Selenium Grid') {
			steps {
				script {
					sh '''
                        docker rm -f selenium-chrome || true
                        docker network create test-network || true
                        docker run -d --name selenium-chrome \
                            --network test-network \
                            -p 4444:4444 \
                            -p 7900:7900 \
                            --shm-size=2g \
                            selenium/standalone-chrome:latest
                    '''
                    sh 'sleep 10'
                }
            }
        }

        stage('Run Tests') {
			steps {
				script {
					sh '''
                        docker run --rm \
                            --network test-network \
                            -v "${WORKSPACE}":/app \
                            -w /app \
                            maven:3.9.2-eclipse-temurin-17 \
                            mvn clean verify \
                                -Drun.mode=remote \
                                -Dselenium.url=${SELENIUM_URL} \
                                -Dbrowser=chrome
                    '''
                }
            }
        }

    }

    post {
		always {
			junit 'target/surefire-reports/*.xml'
            archiveArtifacts artifacts: 'target/cucumber-reports/**', fingerprint: true

            publishHTML(target: [
                reportDir: 'target/cucumber-reports',
                reportFiles: 'index.html',
                reportName: 'Cucumber HTML Report',
                allowMissing: false,
                alwaysLinkToLastBuild: true,
                keepAll: true
            ])
            cucumber buildStatus: 'UNSTABLE',
                failedFeaturesNumber: 1,
                failedScenariosNumber: 1,
                skippedStepsNumber: 1,
                failedStepsNumber: 1,
                classifications: [
                        [key: 'Commit', value: '<a href="${GERRIT_CHANGE_URL}">${GERRIT_PATCHSET_REVISION}</a>'],
                        [key: 'Submitter', value: '${GERRIT_PATCHSET_UPLOADER_NAME}']
                ],
                reportTitle: 'My report',
                fileIncludePattern: '**/*cucumber-report.json',
                sortingMethod: 'ALPHABETICAL',
                trendsLimit: 100
        }
    }
}