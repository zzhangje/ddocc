import { defineConfig } from 'vitepress'

// https://vitepress.dev/reference/site-config
export default defineConfig({
  base: "/ddocc/",
  head: [['link', { rel: 'icon', href: '/ddocc/hammer.png' }]],
  srcDir: "docs",

  title: "DDOCC",
  description: "TBA",
  locales: {
    root: {
      label: 'English',
      lang: 'en-US'
    },
    zh: {
      label: '简体中文',
      lang: 'zh-CN',
      link: '/zh/'
    }
  },
  themeConfig: {
    // https://vitepress.dev/reference/default-theme-config
    logo: '/hammer.png',
    
    nav: [
      { text: 'Home', link: '/' },
    ],

    sidebar: {
      '/': [
        {
          text: 'Tutorial', 
          link: '/',
          items: [
            {
              text: '1. Software Setup and Simulate', 
              link: '/tutorial/1'
            },
            {
              text: '2. Create Your First Subsystem', 
              link: '/tutorial/2'
            },
            {
              text: '3. Create a Primitive Command', 
              link: '/tutorial/3'
            },
            {
              text: '4. Combine a Complex Command', 
              link: '/tutorial/4'
            },
            {
              text: '5. Orchestrate an Autonomous', 
              link: '/tutorial/5'
            }
          ]
        },
      ],
      '/zh/': [
        {
          text: '系列教程', 
          link: '/zh/',
          items: [
            {
              text: '1. 软件配置和仿真运行', 
              link: '/zh/tutorial/0'
            },
            {
              text: '2. 创建第一个子系统', 
              link: '/zh/tutorial/1'
            },
            {
              text: '3. 创建第一个基元命令', 
              link: '/zh/tutorial/2'
            },
            {
              text: '4. 组合一个复杂的命令', 
              link: '/zh/tutorial/3'
            },
            {
              text: '5. 编排一个自动程序', 
              link: '/zh/tutorial/4'
            }
          ]
        },
      ]
    },

    search: {
      provider: 'local',
    },

    socialLinks: [
      { icon: 'github', link: 'https://github.com/zzhangje/ddocc' }
    ]
  }
})
