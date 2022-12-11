terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "4.40.0"
    }
  }
  backend "s3" {
    bucket = "analytics-1042"
    key    = "1042/eksamen-2022.state"
    region = "eu-west-1"
  }
}

provider "aws" {
  access_key = ${{ secrets.AWS_ACCESS_KEY_ID }}
  secret_key = ${{ secrets.AWS_SECRET_ACCESS_KEY }}
  region     = "eu-west-1"
}