execute pathogen#infect() 

call plug#begin('~/.vim/plugged')

Plug 'tpope/vim-sensible'
Plug 'tpope/vim-fugitive'
Plug 'tpope/vim-git'
Plug 'tpope/vim-eunuch'
Plug 'tpope/vim-dispatch'

Plug 'airblade/vim-gitgutter'
Plug 'altercation/vim-colors-solarized'
Plug 'chiel92/vim-autoformat'
Plug 'junegunn/gv.vim'
Plug 'plasticboy/vim-markdown'
Plug 'preservim/nerdtree'
Plug 'raimondi/delimitmate'
Plug 'sbdchd/neoformat'
Plug 'sheerun/vim-polyglot'
Plug 'vim-airline/vim-airline'
Plug 'vim-pandoc/vim-pandoc'
Plug 'vim-pandoc/vim-pandoc-syntax'
Plug 'vim-syntastic/syntastic'
Plug 'vim-test/vim-test'

call plug#end()

syntax on 
syntax enable 
set autoindent
filetype plugin indent on
" show existing tab with 4 spaces width
set tabstop=4
" when indenting with '>', use 4 spaces width
set shiftwidth=4
" On pressing tab, insert 4 spaces
set expandtab

set background=dark
"let g:solarized_termcolors=256 
colorscheme solarized

highlight clear SignColumn
highlight GitGutterAdd ctermfg=LightBlue
highlight GitGutterChange ctermfg=LightYellow
highlight GitGutterDelete ctermfg=LightRed
highlight GitGutterChangeDelete ctermfg=LightYellow

