# Git 常用命令

## 本地仓库常用命令

- **git inti**：初始化本地仓库
- **git status**：查看本地仓库状态
- **git add**：将修改内容添加到暂存区

  > 后面跟文件名：添加指定文件到暂存区

  > **.** ：添加所有修改文件

- **git commit**：将暂存区中的内容提交到本地仓库

  > **-m**：添加备注

- **git log**：显示所有提交过的版本信息

  > --pretty=online：日志展示形式

- **git reflog**：可以查看所有分支的所有操作记录（包括已经被**删除的** commit 记录和 reset 的操作）

### 版本回退

- **git reset --hard** 版本号

### 分支

- **git branch**：分支操作

  > 跟分支名：创建版本库

  > 无参数：查看当前版本库的分支

  > **git branch -d `<branch name>`**：删除指定分支

  > **git branch -a**：查看所有的分支（包括**远程仓库**的分支）

* **git checkout `<branch name>`**：切换分支

  > **git checkout -b `<branch name>`** ：创建+切换分支

* **git switch `<branch name>`**：切换分支

  > **git switch -c `<branch name>`**：创建+切换分支

### 远程仓库

代码托管平台：**github、码云**

#### SSH 公钥

- **cs ~/.ssh**：查看当前计算机是否生成了 ssh
- **ssh-keygen -t rsa -C "18651805393@163.com"**：生成 ssh 公钥
- **cat ~/.ssh/id_rsa.pub**：查看 ssh 公钥

#### 关联远程仓库

- **git remote add 远程名称 远程仓库 URL**

  > **git remote -v**：查看远程仓库信息
  >
  > **git remote remove 远程名称**：移除远程仓库

- **git push -u 仓库名称 分支名**

#### 拉取

- **git clone 仓库地址**
- **git pull 远程仓库 分支名**

#### 删除远程分支

- **git branch -r**：查看**远程**分支

  > gie branch -a 是查看所有分支

- **git branch -r -d origin/`<branch name>`**：删除本地存储的远程分支信息
- **git push origin :`<branch name>`**：将删除的远程分支信息推送到远程仓库，删除远程仓库中存在的分支
