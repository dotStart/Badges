# -*- mode: ruby -*-
# vi: set ft=ruby :
Vagrant.configure("2") do |config|
  config.vm.box = "archlinux/archlinux"

  config.vm.network "forwarded_port", guest: 6379, host: 6379

  config.vm.provider "virtualbox" do |vb|
    vb.memory = "1024"
  end

  config.vm.provision "shell", inline: <<-SHELL
    pacman -Sy
    pacman --noconfirm -S docker redis
    echo "bind 0.0.0.0" >> /etc/redis.conf
    systemctl enable redis
    systemctl restart redis
  SHELL
end
