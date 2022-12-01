terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "4.40.0"
    }
  }
  backend "s3" {
    bucket = "pgr301-2022-eksamen-state"
    key    = "1042/eksamen-actions.state"
    region = "eu-north-1"
  }
}