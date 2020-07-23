Hsin-Ping Hsu (G01167652)
Po-Yen Chen (G01160879)
Douglas Clarke (G01143853)
Nicholas Zempolich (G00702946)



Contributions:
Doug - Docker/Kubernetes
Po - Kubernetes/Rancher, tutorial video
Nick - Jenkins, Docker, Readme file/tutorial compilation
Julia - Jenkins, Docker tutorial video



Below are the workflows that should be followed to setup and replicate the project given the source files included in the zip file. In summary the steps are:
1. Setting up a github repository
2. Ensuring credentials are acquired for the dockerhub repository images should be pushed to
3. Setting up the cluster
4. Setting up Jenkins
5. Building the pipeline


Step 1: Setting up the github
 1. Create a new github repository, the name should be SWE645-group-project and set as public
 2. Once setup the initialize page will ask you to create a new repository for the command line:
 3. Take the attached files and place all of them into a single folder
 4. Once there you'll call the following commands into the command line
	echo '# SWE645-group-project" >> Readme.md
	git init
	git add . 
	git commit -m "swe645 group project"
	git remote add origin https://github.com/YourGithubAccount/SWE645-group-project.git
	git push -u origin master
 5. Once that has been done refresh your github account and your repository should be filled with the files from the folder

Step 2: Setting up Dockerhub:
If you don’t already have a dockerhub account one will need to be created for the project, if you do have one skip this setup.
1. Create a dockerhub and memorize the username and password. Nothing else needs to be done here. 


Step 3: Setting up clusters
 1. Set up a t2 medium ec2 instance on AWS to run the rancher server 
 2. Once that’s done ensure docker is installed and create a docker group
 3. To do this follow the instructions from the following:
 	a. https://docs.docker.com/install/linux/docker-ce/ubuntu/
 	b. https://rancher.com/docs/rancher/v2.x/en/overview/
 	c. Note: Ensure kubectl is installed
 4. On the instance Rancher UI will need to be launched on a container:
 	a. sudo docker run -d --restart=unless-stopped -p 80:80 -p 443:443 rancher/rancher
 5. Once it is launched you can access the Rancher UI through the web browser with address: 0.0.0.0
 6. Once the rancher UI is brought the default user will need a password assigned, do as such.
 7. Then define the RancherServerURl (IP address of the server) - This should be the same as the IP address of your machine as this is how external users can access the machine
 8. Now on the UI page there should be an option to build the cluster
 	a. First click add cluster
 		i. Specify Amazon EC2
 		ii. The cluster name
 		iii. The node must be marked to use all roles.Ensure that it’s using the same region as your ec2 instance, and is of type amazon
 		iv. Then credentials must be setup
 			1. As part of this go into your AWS account, create a new IAM user and give them permission to use EC2
				a. To do this make a IAM user, and give them permissions for EC2 container, full access, container registry, and full access
				b. Then record the access key id and secret key id for the user, these will be put into your credentials in the rancher UI
 		v. Have the node use an automatic security group
 			1. The node instance type should be t3-medium 
 			2. Then you will be asked to make a new IAM role and plug it in
 				a. Here you will need to add a specify policy for the role which can be found at https://rancher.com/docs/rancher/v2.x/en/cluster-provisioning/rke-clusters/options/cloud-providers/#amazon
 				b. There will be specific policies for the worker, etcd and control node.
 				c. You can either make three roles (one for each node type) for each type, or a central  role that suffices for everything. 
 		vi. Once the clusters been made you will need to check amazon as the cloud provider under kubernetes options
 			1. Then finally create the node
 9. You are not done quite yet, the rancher server will create a new a security tag, this needs to be assigned to the ec2 instance to allow proper build
 	a. When the cluster is compiled for the first time, it will on it’s own create and add a new security group to aws called rancher-nodes (this is under step 3 of creating a new cluster)
 	b. This can be done by opening up the security groups for the instance, and turning on the rancher-node security groups.
 10. At this point things should run smoothly. However the kubeconfig file needs to be built. To do this click on the kubeconfig file button on the Rancher UI.
 	a. Copy and paste what’s here and then it will need to be put into ~/.kube/config
 	b. Then download kubectl and set it as the environmental variable
 	c. Confirm it’s been configured appropriately
 	d. Then try to deploy an image directly via the following commands (Note SWE645 here was specified as the name given dockerhub, it should be changed according to your naming infrastructure):
		i. kubectl create deployment swe645 --image=docker.io/swe645docker/swe645-group:latest
 			1. This creates the deployment
		ii. kubectl expose deployment swe645 --type=LoadBalancer --port 80 --target-port 8080
			1. This exposes it and allows people to access it
 			2. When you do this a new security group will be generated in AWS, make sure it is checked as an allowed group
 		iii. kubectl autoscale deployment swe645 --cpu-percent=50  --min=2 --max=5
 			1. This will ensure the cluster autoscales to digest large traffic appropriately. 


Step 4: Setting up Jenkins:
The instance was set up on Linux so the instructions will be given specifically for a linux system

 1. Go to Jenkins.IO and install Jenkins 
 2. Once there go to the linux section (Debian/Ubuntu) - There it will give a specific set of command lines to install linux. 
 	a. Follow the section of wget and below
 3. During the initial installation it will specify a port for the HTTP connector - This is important so note it down.
 4. Once the installation is done go to the http port in your browser (localhost:httpport) and it will ask you to unlock jenkins
 	a. To do so go to the file location of the temporary variable and plug it into the field.
 5. From there it will ask you to make a username and password, do so and write down what you've put down
 6. Then it will ask what plugins you'd like to install, by default you should always select suggested plugins.

Once all of those are installed basic installation is completed, however there are two plugins that MUST be installed for build to work.
 1. ANT (to allow the system to compile temporary war files for new docker images):
 		a. Ant in Workspace
 		b. Ant Plugin
 2. CloudBees Docker Build and Publish plugin

To install these:
 1. Click the manage Jenkins option on the left
 2. Then to Manage Plugins
 3. Available, then search for the plugins
 	a. If you can't find them check the installed tab to confirm if they're already installed on the Jenkins instance.

Finalizing the ANT installation:
 1. While the plugins already been installed ANT still needs one final step to be installed
 2. To do this go to Global tool configuration under manage jenkins
 3. Once here go to the Ant Section:
 	a. Click Add Ant
 	b. Specify the name as Ant1.10.7 and ensure install automatically is checked (and that it installs from apache)
 	c. Once there click save and it should install automatically. 


Credentials - As part of this project the newly compiled docker images will need to be sent to dockerhub as such credentials for the site must be specified:
 1. Click Credentials on the left side of the screen in Jenkins
 2. Click Add Credentials and fill out the form:
 	a. The scope should be global
 	b. The username and password should be the username and password for the dockerhub account images should be pushed to (Which is your already existing dockerhub account or the one you created earlier)
 	c. The ID should be an ID that is easily remembered (In the Jenkins file we used dockerhub, this should be used to minimize code changes needed). 
	

Step 5: Setting up the pipeline:
The Jenkins file uses a combination of DockerHub Deployments, Ant and build.xml  to generate the newest dockerfile when code changes are pushed to github. The build.xml is located under the SWE645-group directory in the attached files. First it will copy the github files and use Ant as a build tool to compile a temporary war file that will be used to generate a new docker image. If the Docker image was generated correctly it will then be pushed to dockerhub. Finally that new docker image will be extracted by the jenkins pipeline, and deployed via kubectl onto the cluster to replace the out of date image that was previously deployed. 

 1. Adjusting the Jenkins File: This will be one of the easiest parts as the Jenkins file which is the base of the pipeline is already supplied. However the following things must be done to ensure it works correctly:
 	a. Line 3 of the Jenkins file specifies the registry: This needs to be changed to Registry under environment, must be set to the dockerhub repository (in your account) where the image should be stored.
 	b. Line 4 of the Jenkins file has registryCredential = “dockerhub”. If this is not the credential name you’ve used to store dockerhub credentials, it needs to be changed. 
 	c. Line 67 and 69 have similair issues, the name of the docker registry should be changed to the one specified in line 3.
 	d. The fist line git under the stage 'cloning git' must be set to your current git repository, if this is not adjusted it will not build correctly
 2. Creating the Pipeline in Jenkins:
 	a. First confirm that the Jenkins file is placed into the github as appropriate (and code is adjusted as in the previous step)
 	b. Create a new item in Jenkins, specifically a pipeline
 	c. Under general-build triggers click github hook trigger for GITScm polling
 	d. Then under pipelines:
 		i. Set the definition as pipeline script from SCM
 		ii. SCM as Git
 		iii. Under repository insert the URL of the github.git
 		iv. Under scriptpath specify Jenkinsfile
 3. Setting the github hook:
 	a. Go to the repository and got to the settings tab
 	b. Under settings go to webhook and click add webhook
 	c. There the payload URL will be:
 		i. https://IPADDRESS:PORTNAME/github-webhook
 		ii. Where IPaddress is the IP address Jenkins is on
 		iii. PortName is the port Jenkins is running on
 	d. Content type will be application/json
 	e. Ensure just the push event will activate it
 	f.Click add webhook
 4. Setting up the AWS instance:
 	a. After that is done the AWS instance needs to be adjusted so it has an inbound rule to accept github requests
 	b. Here click on your instance and go to IAM groups and create a new security group
 		i. This will have a single inbound rule that will:
 			1. Have a type of anywhere
 			2. Portrange of PORT Jenkins runs on
 			3. Source - anywhere
 			4. Then create the security group
	c. Once that is complete you will have to add this new security group to your instance
		
Finally build the project manually from Jenkins and confirm it updates the docker image. 


Youtube tutorial links:
Github: https://www.youtube.com/watch?v=_qcKe6nBfNE
Ranchers Cluster buildup: https://www.youtube.com/watch?v=jF8jCg1WPwo
Jenkins Installation: https://www.youtube.com/watch?v=twkRYe6m8DQ
Build Jenkins: https://www.youtube.com/watch?v=_MIssWPguZ0
Github webhook trigger: https://www.youtube.com/watch?v=xlvSjDHvUwU
