terraform {
  backend "s3" {
    bucket = "otc-tf"
    key    = "theagainagain/terraform.tfstate"
    region = "us-west-2"
  }
}

data "terraform_remote_state" "otc" {
  backend = "s3"
  config {
    bucket = "otc-tf"
    key    = "theagainagain/terraform.tfstate"
    region = "us-west-2"
  }
}
