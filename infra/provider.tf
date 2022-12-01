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
    region = "eu-north-1"
  }
}